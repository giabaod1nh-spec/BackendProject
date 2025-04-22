package com.example.trainning.point.service.impl;

import com.example.trainning.point.dto.request.AuthenticationRequest;
import com.example.trainning.point.dto.request.IntrospectRequest;
import com.example.trainning.point.dto.request.LogoutRequest;
import com.example.trainning.point.dto.request.RefreshRequest;
import com.example.trainning.point.dto.response.AuthenticationResponse;
import com.example.trainning.point.dto.response.IntrospectResponse;
import com.example.trainning.point.entity.InvalidatedToken;
import com.example.trainning.point.entity.User;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.repository.InvalidatedTokenRepository;
import com.example.trainning.point.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @NonFinal //Đánh đấu để ko bị inject vào constructor
    private static final String SIGNER_KEY =
            "F82LVYKeAtDgteFr0CdUy4EIFvzhIGTqf3nSVGXu9Pw5f+jLWHU32aSqfXHfaOVm" ;

    public IntrospectResponse introspect(IntrospectRequest request)
            throws ParseException, JOSEException {
        var token = request.getToken();

        //verifyToken(token , false);
        boolean isValid = true;

        try{
           verifyToken(token , false);
        } catch (AppException e){
            isValid = false;
        }
        //Verify token
        //JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

       // SignedJWT signedJWT = SignedJWT.parse(token);

        //Check token expired
        //Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        //Sau khi co verifier va parse -> verify
      // var verified = signedJWT.verify(verifier); //-> tra ve boolean

        return IntrospectResponse.builder()
               .valid(isValid)
                .build();

       // return IntrospectResponse.builder()
          //      .valid(verified && expiryTime.after(new Date()))
           //     .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // ➕ In log sau khi tìm được user
        System.out.println("Tìm được user: " + user.getEmail());

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());



        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHETICATED);

        var token = generateToken(user);
        List<String> roles = new ArrayList<>();
        if(user.getRoles() != null){
            for (var it: user.getRoles())
                roles.add(it.getName());
        }

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .roles(roles)
                .email(user.getEmail())
                .userId(user.getUserId())
                .build();
    }


    //Create method logout



    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        //can doc cac claim nhu token id va expiryTime cua token
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .id(jit)
                    .expiryTime(expiryTime)
                    .build();

            invalidatedTokenRepository.save(invalidatedToken);
        }catch (AppException exception){
                 log.info("Token already expired");
        }
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken() , false);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user = userRepository.findByEmail(username).orElseThrow(
                () -> new AppException(ErrorCode.UNAUTHETICATED)
        );
        var token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    private String generateToken(User user){
        //Header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        //Claimset
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("simple.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .claim("scope" , buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .build();
        //Sau khi du claimset tao payload
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header , payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token" , e);
            throw new RuntimeException(e);
        }

    }

    //Ham verifyToken tra ve 1 signedjwtToken
    private SignedJWT verifyToken(String token , boolean isRefresh) throws ParseException, JOSEException {
        //Verify token
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        //Check token expired
        Date expiryTime  = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        //Sau khi co verifier va parse -> verify
        var verified = signedJWT.verify(verifier); //-> tra ve boolean

        if (!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHETICATED);

        if (invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHETICATED);;
        return signedJWT;
    }

    private String buildScope(User user){
        //Scope la 1 list dung Stringjoiner
        StringJoiner stringJoiner = new StringJoiner(" ");
        //Convention cach nhau bang 1 dau cach
       if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                //Thêm prefix Role để clear hơn giữa role và permissions
                if(!CollectionUtils.isEmpty(role.getPermissions()))
                     role.getPermissions()
                        .forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }
}

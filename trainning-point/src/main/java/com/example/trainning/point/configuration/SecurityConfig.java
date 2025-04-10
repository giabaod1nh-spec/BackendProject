package com.example.trainning.point.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//phân quyền trên method , dat annotation tren method
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {"/user" ,
            "/auth/token" , "/auth/introspect"
    };

    //@NonFinal //Đánh đấu để ko bị inject vào constructor
    private static final String SIGNER_KEY =
            "F82LVYKeAtDgteFr0CdUy4EIFvzhIGTqf3nSVGXu9Pw5f+jLWHU32aSqfXHfaOVm" ;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //Config API nay co the duoc truy cap public
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                      //  .requestMatchers(HttpMethod.GET, "/user").hasAuthority("ROLE_ADMIN")//CHI ADMIN MOI DUOC TRUY CAP ENDPOINT NAY
                       //.hasRole("ADMIN") tim trong token co ADMIN la pass
                        .anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())  //Khi authen failed thi dieu huong user ?
        );
        //decoder la 1 interface de decode token , need a method tu define

        //Van chua POST duoc, bat csrf protect endpoint from attack
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //Ko can thiet , disable
        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes() , "HS512");

        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        //Customize authority mapper cho converter
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        //Vì đã manage r nên ko cần thêm prefix ở đây trong service

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        //Set customize converter
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);


        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder (){

        return new BCryptPasswordEncoder(10);
    }

}

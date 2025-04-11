package com.example.trainning.point.service;

import com.example.trainning.point.dto.request.UserUpdateRequest;
import com.example.trainning.point.dto.response.UserResponse;
import com.example.trainning.point.enums.Role;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.UserMapper;
import com.example.trainning.point.repository.RoleRepository;
import com.example.trainning.point.repository.UserRepository;
import com.example.trainning.point.dto.request.UserCreationRequest;
import com.example.trainning.point.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request){
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());  //add roles vao cho user
       // user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo(){
       var context = SecurityContextHolder.getContext();
       String name =  context.getAuthentication().getName();

       User user =  userRepository.findByEmail(name).orElseThrow(
               () -> new AppException(ErrorCode.USER_NOT_FOUND));

       return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId , UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(() ->  new RuntimeException("User not found"));

        userMapper.updateUser(user , request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize(("hasAuthority('UPDATE_DATA')"))
    //Spring create 1 proxy to check hasRole... -> pass
    public List<UserResponse> getUsers(){
        log.info("In method get Users");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse getUser(String userId){
        log.info("In method get user by id");
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(()-> new AppException((ErrorCode.USER_NOT_FOUND))));
    }

}

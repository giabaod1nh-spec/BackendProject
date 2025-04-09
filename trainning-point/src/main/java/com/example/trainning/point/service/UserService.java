package com.example.trainning.point.service;

import com.example.trainning.point.dto.request.UserUpdateRequest;
import com.example.trainning.point.dto.response.UserResponse;
import com.example.trainning.point.enums.Role;
import com.example.trainning.point.mapper.UserMapper;
import com.example.trainning.point.repository.UserRepository;
import com.example.trainning.point.dto.request.UserCreationRequest;
import com.example.trainning.point.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserResponse createUser(UserCreationRequest request){
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());  //add roles vao cho user
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String userId , UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(() ->  new RuntimeException("User not found"));

        userMapper.updateUser(user , request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    public UserResponse getUser(String userId){
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found")));
    }

}

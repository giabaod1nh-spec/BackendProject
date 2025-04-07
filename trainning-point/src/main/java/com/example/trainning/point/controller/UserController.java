package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.ApiResponse;
import com.example.trainning.point.dto.request.UserCreationRequest;
import com.example.trainning.point.dto.request.UserUpdateRequest;
import com.example.trainning.point.dto.response.UserResponse;
import com.example.trainning.point.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createUser(request));

        return apiResponse;
    }

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }

    @GetMapping
    List<UserResponse> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable("userId") String userId , @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId , request);
    }

}

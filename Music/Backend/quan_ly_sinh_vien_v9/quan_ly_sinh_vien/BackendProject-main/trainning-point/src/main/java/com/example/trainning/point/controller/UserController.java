package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.ApiResponse;
import com.example.trainning.point.dto.request.UserCreationRequest;
import com.example.trainning.point.dto.request.UserUpdateRequest;
import com.example.trainning.point.dto.request.user.PasswordRequest;
import com.example.trainning.point.dto.request.user.UserRequest;
import com.example.trainning.point.dto.request.user.UserSearch;
import com.example.trainning.point.dto.response.UserResponse;
import com.example.trainning.point.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

//    @PostMapping
//    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
//        return ApiResponse.<UserResponse>builder()
//                .result(userService.createUser(request))
//                .build();
//    }

    @Operation(summary = "create user info")
    @PostMapping("/create")
    ApiResponse<UserResponse> createUserCustom(@RequestBody @Valid UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.creatUser(request))
                .build();
    }

    @Operation(summary = "update user info")
    @PutMapping("/update/{id}")
    ApiResponse<UserResponse> updateUserCustom(@PathVariable("id") String userId , @RequestBody UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId , request))
                .build();
    }



    @Operation(summary = "update pass info")
    @PutMapping("/update-pass/{id}")
    ApiResponse<UserResponse> updatePassCustom(@RequestBody PasswordRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.changePassword(request))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId){

        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @Operation(summary = "Get all and search")
    @GetMapping("/list")
    ApiResponse<List<UserResponse>> getUsers(@RequestParam(name = "keySearch", required = false) String keySearch,
                                             @RequestParam(name = "roleName", required = false)String roleName,
                                             @RequestParam(name = "isDelete", required = false) Boolean isDelete){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Email: {}" , authentication.getName()); // log ra emali va auth like admin , ...
        authentication.getAuthorities()
                .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        UserSearch userSearch = UserSearch.builder()
                .keySearch(keySearch)
                .roleName(roleName)
                .isDelete(isDelete)
                .build();

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers(userSearch))
                .build();
    }

    @Operation(summary = "Get all page and search")
    @GetMapping("/pagnition")
    ApiResponse<Page<UserResponse>> getUsersPage(@RequestParam(name = "keySearch", required = false) String keySearch,
                                                 @RequestParam(name = "roleName", required = false)String roleName,
                                                 @RequestParam(name = "isDelete", required = false) Boolean isDelete,
                                                 @RequestParam(name = "page", defaultValue = "1") int page,
                                                 @RequestParam(name = "limit", defaultValue = "5") int limit){
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        log.info("Email: {}" , authentication.getName()); // log ra emali va auth like admin , ...
//        authentication.getAuthorities()
//                .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        Pageable pageable = PageRequest.of(page - 1, limit);

        UserSearch userSearch = UserSearch.builder()
                .keySearch(keySearch)
                .roleName(roleName)
                .isDelete(isDelete)
                .build();

        return ApiResponse.<Page<UserResponse>>builder()
                .result(userService.getUsers(userSearch, pageable))
                .build();
    }

//    @PutMapping("/{userId}")
//    ApiResponse<UserResponse> updateUser(@PathVariable("userId") String userId , @RequestBody UserUpdateRequest request){
//        return ApiResponse.<UserResponse>builder()
//                .result(userService.updateUser(userId , request))
//                .build();
//    }

    @Operation(summary = "delete usser")
    @DeleteMapping("/delete/{id}")
    ApiResponse<Boolean> updatePassCustom(@PathVariable String id){
        userService.deleteUser(id);
        return ApiResponse.<Boolean>builder()
                .result(true)
                .build();
    }

    @Operation(summary = "active user info")
    @PostMapping("/active/{id}")
    ApiResponse<Boolean> activeUser(@PathVariable("id") String userId ){
        userService.activeUser(userId);
        return ApiResponse.<Boolean>builder()
                .result(true)
                .build();
    }
}

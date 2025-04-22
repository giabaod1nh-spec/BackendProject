package com.example.trainning.point.service.interfaces;

import com.example.trainning.point.dto.request.UserCreationRequest;
import com.example.trainning.point.dto.request.UserUpdateRequest;
import com.example.trainning.point.dto.request.user.PasswordRequest;
import com.example.trainning.point.dto.request.user.UserRequest;
import com.example.trainning.point.dto.request.user.UserSearch;
import com.example.trainning.point.dto.response.UserResponse;
import com.example.trainning.point.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
//    public UserResponse createUser(UserCreationRequest request);
    Long count();
    User findByEmail(String email);
    public UserResponse creatUser(UserRequest request);
    public UserResponse getMyInfo();
//    public UserResponse updateUser(String userId , UserUpdateRequest request);
    public UserResponse updateUser(String userId , UserRequest request);
    UserResponse changePassword(PasswordRequest request);
    public List<UserResponse> getUsers(UserSearch userSearch);
    Page<UserResponse> getUsers(UserSearch userSearch, Pageable pageable);
    public UserResponse getUser(String userId);

    void deleteUser(String id);
    void activeUser(String id);
}

package com.example.trainning.point.mapper;

import com.example.trainning.point.dto.request.UserCreationRequest;
import com.example.trainning.point.dto.request.UserUpdateRequest;
import com.example.trainning.point.dto.response.UserResponse;
import com.example.trainning.point.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //@Mapping(target = "active" , ignore = true)
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles" , ignore = true)
    //ko map set roles ma tu phai map
    void updateUser(@MappingTarget User user , UserUpdateRequest request);
}

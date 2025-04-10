package com.example.trainning.point.mapper;

import com.example.trainning.point.dto.request.PermissionRequest;
import com.example.trainning.point.dto.request.UserCreationRequest;
import com.example.trainning.point.dto.request.UserUpdateRequest;
import com.example.trainning.point.dto.response.PermissionResponse;
import com.example.trainning.point.dto.response.UserResponse;
import com.example.trainning.point.entity.Permission;
import com.example.trainning.point.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}

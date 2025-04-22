package com.example.trainning.point.mapper;

import com.example.trainning.point.dto.request.PermissionRequest;
import com.example.trainning.point.dto.request.RoleRequest;
import com.example.trainning.point.dto.response.PermissionResponse;
import com.example.trainning.point.dto.response.RoleResponse;
import com.example.trainning.point.entity.Permission;
import com.example.trainning.point.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions" , ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}

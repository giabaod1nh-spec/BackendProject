package com.example.trainning.point.service.impl;

import com.example.trainning.point.dto.request.RoleRequest;
import com.example.trainning.point.dto.response.RoleResponse;
import com.example.trainning.point.entity.Role;
import com.example.trainning.point.mapper.RoleMapper;
import com.example.trainning.point.repository.PermissionRepository;
import com.example.trainning.point.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public Role findEntityByName(String name){
        return roleRepository.findByName(name);
    }

    public RoleResponse findByName(String name){
        Role role = roleRepository.findByName(name);
        if(role == null)
            return null;
        return roleMapper.toRoleResponse(role);
    }

    public RoleResponse create(RoleRequest request){
         var role = roleMapper.toRole(request);

          var permissions =  permissionRepository.findAllById(request.getPermissions());
          role.setPermissions(new HashSet<>(permissions));

          role = roleRepository.save(role);
          return  roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        var roles = roleRepository.findAll();
        return roles.stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public void delete(String role){
         roleRepository.deleteById(role);
    }
}

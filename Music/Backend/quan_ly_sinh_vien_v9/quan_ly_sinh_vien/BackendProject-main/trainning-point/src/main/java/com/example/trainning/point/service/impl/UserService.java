package com.example.trainning.point.service.impl;

import com.example.trainning.point.dto.request.UserUpdateRequest;
import com.example.trainning.point.dto.request.user.PasswordRequest;
import com.example.trainning.point.dto.request.user.UserRequest;
import com.example.trainning.point.dto.request.user.UserSearch;
import com.example.trainning.point.dto.response.UserResponse;
import com.example.trainning.point.entity.Role;
import com.example.trainning.point.enums.RoleEnum;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.UserMapper;
import com.example.trainning.point.mapper.custom.UserMapperCustom;
import com.example.trainning.point.repository.RoleRepository;
import com.example.trainning.point.repository.UserRepository;
import com.example.trainning.point.dto.request.UserCreationRequest;
import com.example.trainning.point.entity.User;
import com.example.trainning.point.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@Slf4j
public class UserService implements IUserService {
    UserRepository userRepository;
    UserMapper userMapper;
    UserMapperCustom userMapperCustom;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    RoleService roleService;

//    public UserResponse createUser(UserCreationRequest request){
//        User user = userMapper.toUser(request);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//        HashSet<String> roles = new HashSet<>();
//        roles.add(RoleEnum.USER.name());  //add roles vao cho user
//       // user.setRoles(roles);
//
//        return userMapper.toUserResponse(userRepository.save(user));
//    }

    @Override
    public UserResponse creatUser(UserRequest request) {
        User entity = userMapperCustom.convertToEntity(request);
        return userMapperCustom.convertToResponse(userRepository.save(entity));
    }

    public UserResponse getMyInfo(){
       var context = SecurityContextHolder.getContext();
       String name =  context.getAuthentication().getName();

       User user =  userRepository.findByEmail(name).orElseThrow(
               () -> new AppException(ErrorCode.USER_NOT_FOUND));

       return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(String userId, UserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() ->  new RuntimeException("User not found"));

        Set<Role> roles = new HashSet<>();
        if (request.getRoleNames() != null){
            for(var it: request.getRoleNames()){
                Role role = roleService.findEntityByName(it);
                if (role != null)
                    roles.add(role);
            }
        }

        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setDob(request.getDob());
        user.setPhone(request.getPhone());
        user.setGender(request.getGender());
        user.setRoles(roles);

        return userMapperCustom.convertToResponse(userRepository.save(user));
    }

    @Override
    public Long count() {
        return userRepository.count();
    }

    @Override
    public User findByEmail(String email) {
        var entity = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(entity.getIsDelete() == true)
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        return entity;
    }

    @Override
    public UserResponse changePassword(PasswordRequest request) {
        var entity = this.findByEmail(request.getEmail());

        if(!passwordEncoder.matches(request.getOldPass(), entity.getPassword()))
            throw new AppException(ErrorCode.PASS_NOT_MATCH);
        entity.setPassword(passwordEncoder.encode(request.getNewPass()));
        return userMapperCustom.convertToResponse(userRepository.save(entity));
    }

//    public UserResponse updateUser(String userId , UserUpdateRequest request){
//        User user = userRepository.findById(userId).orElseThrow(() ->  new RuntimeException("User not found"));
//
//        userMapper.updateUser(user , request);
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//        var roles = roleRepository.findAllById(request.getRoles());
//        user.setRoles(new HashSet<>(roles));
//
//        return userMapper.toUserResponse(userRepository.save(user));
//    }

//    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize(("hasAuthority('UPDATE_DATA')"))
    //Spring create 1 proxy to check hasRole... -> pass
    public List<UserResponse> getUsers(UserSearch userSearch){
//        log.info("In method get Users");
        return userRepository.findAll(userSearch.getKeySearch(), userSearch.getRoleName(), userSearch.getIsDelete())
                .stream()
                .map(userMapperCustom::convertToResponse).toList();
    }

    @Override
    public Page<UserResponse> getUsers(UserSearch userSearch, Pageable pageable) {
        return userRepository.findAllPage(userSearch.getKeySearch(), userSearch.getRoleName(), userSearch.getIsDelete(), pageable)
                .map(userMapperCustom::convertToResponse);
    }

//    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse getUser(String userId){
        log.info("In method get user by id");
        return userMapperCustom.convertToResponse(userRepository.findById(userId)
                .orElseThrow(()-> new AppException((ErrorCode.USER_NOT_FOUND))));
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setIsDelete(true);
        userRepository.save(user);
    }

    @Override
    public void activeUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setIsDelete(false);
        userRepository.save(user);
    }
}

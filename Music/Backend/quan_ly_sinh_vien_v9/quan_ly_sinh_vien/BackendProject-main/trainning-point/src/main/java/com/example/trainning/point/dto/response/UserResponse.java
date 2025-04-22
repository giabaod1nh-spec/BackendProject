package com.example.trainning.point.dto.response;

import com.example.trainning.point.entity.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String userId;
    String code;
    String fullName;
    LocalDate dob;
    String email;
    String phone;
    Boolean active;
    String gender;
    Boolean isDelete;

    Long classId;
    String className;
    String corhort;
    Set<RoleResponse> roles;
    String roleNames;
    String evaluResult;
}

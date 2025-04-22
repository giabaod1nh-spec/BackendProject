package com.example.trainning.point.dto.request.user;

import com.example.trainning.point.entity.Gender;
import com.example.trainning.point.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    String code;

    String email;
    String password;
    String fullName;
    LocalDate dob;
    String phone;
    Gender gender;

    Set<String> roleNames;
}

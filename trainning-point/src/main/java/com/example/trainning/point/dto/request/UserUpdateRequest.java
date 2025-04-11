package com.example.trainning.point.dto.request;

import com.example.trainning.point.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String fullName;
    @DobConstraint(min = 2 , message = "INVALID_DOB")
    LocalDate dob;
    String phone;
    List<String> roles;
}

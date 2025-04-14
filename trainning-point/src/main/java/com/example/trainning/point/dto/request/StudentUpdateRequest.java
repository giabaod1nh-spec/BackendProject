package com.example.trainning.point.dto.request;

import com.example.trainning.point.entity.Gender;
import com.example.trainning.point.validator.DobConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentUpdateRequest {
        String fullName;
        @DobConstraint(min = 2 , message = "INVALID_DOB")
        LocalDate dob;
        Gender gender;
        String phone;
        String className;
        Boolean isActive;
}

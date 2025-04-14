package com.example.trainning.point.dto.response;

import com.example.trainning.point.entity.Gender;
import com.example.trainning.point.entity.Semester;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
    Long studentId;
    String className;
    String fullName;
    LocalDate dob;
    Gender gender;
    String phone;
    Boolean isActive;
}

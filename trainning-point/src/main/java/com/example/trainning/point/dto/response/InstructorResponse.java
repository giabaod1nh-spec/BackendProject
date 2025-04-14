package com.example.trainning.point.dto.response;

import com.example.trainning.point.entity.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructorResponse {
    Long instructorId;
    String className;
    String fullName;
    LocalDate dob;
    Gender gender;
    String phone;
    Boolean isActive;
}

package com.example.trainning.point.dto.request;

import com.example.trainning.point.entity.Gender;
import com.example.trainning.point.validator.DobConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructorCreationRequest {
      @NotNull(message = "UserID is required")
      String userId;
      String fullName;
      @DobConstraint(min = 2 , message = "INVALID_DOB")
      LocalDate dob;
      Gender gender;
      String phone;
      String className;
      Boolean isActive;
}

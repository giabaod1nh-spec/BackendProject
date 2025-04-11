package com.example.trainning.point.dto.request;

import com.example.trainning.point.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String email ;
    @Size(min = 6 , message = "INVALID_PASSWORD")
    String password;
    String fullName;
    @DobConstraint(min = 2 , message = "INVALID_DOB")
    LocalDate dob;
    String phone;
    Boolean active = true;
}

package com.example.trainning.point.dto.request;

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
    LocalDate dob;
    String phone;
    boolean active;
}

package com.example.trainning.point.dto.request;

import com.example.trainning.point.entity.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminCreationRequest {
     String userId;
     Gender gender;
}

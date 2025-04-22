package com.example.trainning.point.dto.request.semester;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SemesterRequest {
    String name;
    LocalDate startDate;
    LocalDate endDate;
}

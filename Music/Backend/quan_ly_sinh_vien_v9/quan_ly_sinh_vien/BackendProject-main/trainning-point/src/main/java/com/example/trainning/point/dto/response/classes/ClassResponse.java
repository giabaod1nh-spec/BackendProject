package com.example.trainning.point.dto.response.classes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassResponse {
    Long id;
    String codeClass;
    String name;
    String academicCohort;
    LocalDate startDate;
    LocalDate endDate;
}

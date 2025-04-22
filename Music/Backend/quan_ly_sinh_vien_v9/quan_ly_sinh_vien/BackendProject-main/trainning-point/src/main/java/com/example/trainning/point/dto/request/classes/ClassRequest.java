package com.example.trainning.point.dto.request.classes;

import com.example.trainning.point.entity.ClassManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassRequest {
    String codeClass;
    String name;
    String academicCohort;
    LocalDate startDate;
    LocalDate endDate;
}

package com.example.trainning.point.mapper.custom;

import com.example.trainning.point.dto.request.classes.ClassRequest;
import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.response.classes.ClassResponse;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.entity.ClassManager;
import com.example.trainning.point.entity.EvalutionCategory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClassMapper {
    public ClassManager convertToEntity(ClassRequest request){

        return ClassManager.builder()
                .codeClass(request.getCodeClass())
                .name(request.getName())
                .academicCohort(request.getAcademicCohort())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    public ClassResponse convertToResponse(ClassManager entity){

        return ClassResponse.builder()
                .id(entity.getId())
                .codeClass(entity.getCodeClass())
                .name(entity.getName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}

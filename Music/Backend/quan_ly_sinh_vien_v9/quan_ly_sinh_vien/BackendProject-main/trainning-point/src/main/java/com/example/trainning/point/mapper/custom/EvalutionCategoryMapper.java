package com.example.trainning.point.mapper.custom;

import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionResponse;
import com.example.trainning.point.dto.response.evalution.standard.EvalutionStandardResponse;
import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.EvalutionPerson;
import com.example.trainning.point.entity.EvalutionStandard;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EvalutionCategoryMapper {
    EvalutionStandardMapper evalutionStandardMapper;
    EvalutionPersonMapper evalutionPersonMapper;

    public EvalutionCategory convertToEntity(EvalutionCategoryRequest request){

        return EvalutionCategory.builder()
                .name(request.getName())
                .maxPoint(request.getMaxPoint())
                .build();
    }

    public EvalutionCategoryResponse convertToResponse(EvalutionCategory entity){

        return EvalutionCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .maxPoint(entity.getMaxPoint())
                .build();
    }

    public EvalutionResponse convertToEvalutionResponse(EvalutionCategory entity, String userId){
        List<EvalutionStandardResponse> standardResponseList = new ArrayList<>();
        if (entity.getEvalutionStandardList() != null){
            for (var it: entity.getEvalutionStandardList()){
                EvalutionStandardResponse response = evalutionStandardMapper.convertToResponse(it, userId);
                standardResponseList.add(response);
            }
        }

        return EvalutionResponse.builder()
                .idCategory(entity.getId())
                .nameCategory(entity.getName())
                .maxPoint(entity.getMaxPoint())
                .standards(standardResponseList)
                .build();
    }
}

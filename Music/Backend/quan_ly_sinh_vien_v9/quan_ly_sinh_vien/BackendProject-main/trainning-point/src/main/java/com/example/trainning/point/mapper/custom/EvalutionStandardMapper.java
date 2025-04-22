package com.example.trainning.point.mapper.custom;

import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.request.evalution.standard.EvalutionStandardRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionPersonResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionResponse;
import com.example.trainning.point.dto.response.evalution.standard.EvalutionStandardResponse;
import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.EvalutionPerson;
import com.example.trainning.point.entity.EvalutionStandard;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.repository.IEvalutionCategoryRepository;
import com.example.trainning.point.repository.IEvalutionPersonRepository;
import com.example.trainning.point.service.interfaces.IEvalutionCategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EvalutionStandardMapper {
    IEvalutionCategoryRepository evalutionCategoryRepository;
    EvalutionPersonMapper evalutionPersonMapper;
    IEvalutionPersonRepository evalutionPersonRepository;

    public EvalutionStandard convertToEntity(EvalutionStandardRequest request) {
        EvalutionCategory category = evalutionCategoryRepository
                .findById(request.getEvalutionCategoryId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return EvalutionStandard.builder()
                .name(request.getName())
                .minPoint(request.getMinPoint())
                .maxPoint(request.getMaxPoint())
                .evalutionCategory(category)
                .build();
    }

    public EvalutionStandardResponse convertToResponse(EvalutionStandard entity) {
//        List<EvalutionPersonResponse> evalutionPerson = new ArrayList<>();
//        if (entity.getEvalutionPersonList() != null){
//            for (var evaluPerson: entity.getEvalutionPersonList()){
//                var ePersonRespons = evalutionPersonMapper.convertToResponse(evaluPerson);
//                evalutionPerson.add(ePersonRespons);
//            }
//        }


        return EvalutionStandardResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .minPoint(entity.getMinPoint())
                .maxPoint(entity.getMaxPoint())
                .evalutionCategoryId(entity.getEvalutionCategory().getId())
                .evalutionCategoryName(entity.getEvalutionCategory().getName())
                .build();
    }

    public EvalutionStandardResponse convertToResponse(EvalutionStandard entity, String userId) {
//        List<EvalutionPersonResponse> evalutionPerson = new ArrayList<>();
//        if (entity.getEvalutionPersonList() != null) {
//            for (var evaluPerson : entity.getEvalutionPersonList()) {
//                var ePersonRespons = evalutionPersonMapper.convertToResponse(evaluPerson);
//
//                if (userId == null) {
//                    evalutionPerson.add(ePersonRespons);
//                } else if (ePersonRespons.getUserId() != null && ePersonRespons.getUserId().equals(userId))
//                    evalutionPerson.add(ePersonRespons);
//            }
//        }

        EvalutionPerson evalutionPerson = new EvalutionPerson();
        if (entity.getEvalutionPersonList() != null) {
            for (var evaluPerson : entity.getEvalutionPersonList()) {
                if(evaluPerson.getUser() != null && evaluPerson.getUser().getUserId() != null && evaluPerson.getUser().getUserId().equals(userId)){
                    evalutionPerson = evaluPerson;
                    break;
                }
            }
        }

        return EvalutionStandardResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .minPoint(entity.getMinPoint())
                .maxPoint(entity.getMaxPoint())
                .evalutionCategoryId(entity.getEvalutionCategory().getId())
                .evalutionPerson(evalutionPersonMapper.convertToResponse(evalutionPerson))
                .evalutionCategoryName(entity.getEvalutionCategory().getName())
                .build();
    }

}

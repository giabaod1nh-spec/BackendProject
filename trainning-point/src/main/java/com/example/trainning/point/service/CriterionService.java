package com.example.trainning.point.service;

import com.example.trainning.point.dto.request.CriterionCreationRequest;
import com.example.trainning.point.dto.request.CriterionUpdateRequest;
import com.example.trainning.point.dto.response.CriterionResponse;
import com.example.trainning.point.entity.Criterion;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.CriterionMapper;
import com.example.trainning.point.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@Service
public class CriterionService {
    CriterionRepository criterionRepository;
    CriterionMapper criterionMapper;
    CriterionPointRepository criterionPointRepository;

    public CriterionResponse createCriterion(CriterionCreationRequest request) {
        Criterion criterion = criterionMapper.toCriterion(request);

       if (criterionRepository.existsByDescription(request.getDescription())){
           throw new AppException(ErrorCode.CRITERION_EXISTED);
       }
        // if(instructorRepository.existsByUser(user)){
        // throw new AppException(ErrorCode.STUDENT_EXISTED);
         //}
        return criterionMapper.toCriterionResponse(criterionRepository.save(criterion));
    }
    public CriterionResponse getCriterion(@PathVariable Long criterionId){
        return criterionMapper.toCriterionResponse(criterionRepository.findByCriterionId(criterionId)
                .orElseThrow(() -> new AppException(ErrorCode.CRITERION_NOT_EXISTED) ));
    }

    public List<CriterionResponse> getAllCriterion(){
        return criterionRepository.findAll().stream()
                .map(criterionMapper::toCriterionResponse).toList();
    }

    public CriterionResponse updateCriterion(@PathVariable Long criterionId , CriterionUpdateRequest request){
        Criterion criterion = criterionRepository.findByCriterionId(criterionId).orElseThrow(() -> new AppException(ErrorCode.CRITERION_NOT_EXISTED));
        criterionMapper.updateCriterion(criterion , request);

        var criterionPoints = criterionPointRepository.findByCriterionPointId(criterionId);
        criterion.setCriterionPoints(criterionPoints);

        return criterionMapper.toCriterionResponse(criterionRepository.save(criterion));
    }

}


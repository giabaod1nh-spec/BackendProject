package com.example.trainning.point.service;

import com.example.trainning.point.dto.request.CriterionPointCreationRequest;
import com.example.trainning.point.dto.request.CriterionPointUpdateRequest;
import com.example.trainning.point.dto.request.PermissionRequest;
import com.example.trainning.point.dto.response.CriterionPointResponse;
import com.example.trainning.point.dto.response.CriterionResponse;
import com.example.trainning.point.dto.response.PermissionResponse;
import com.example.trainning.point.entity.CriterionPoint;
import com.example.trainning.point.entity.Permission;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.CriterionMapper;
import com.example.trainning.point.mapper.CriterionPointMapper;
import com.example.trainning.point.mapper.PermissionMapper;
import com.example.trainning.point.repository.CriterionPointRepository;
import com.example.trainning.point.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class CriterionPointService {

    CriterionPointRepository criterionPointRepository;
    CriterionPointMapper criterionPointMapper;

    public CriterionPointResponse create(CriterionPointCreationRequest request){
        CriterionPoint criterionPoint = criterionPointMapper.toCriterionPoint(request);

        criterionPoint = criterionPointRepository.save(criterionPoint);
        return criterionPointMapper.toCriterionPointResponse(criterionPointRepository.save(criterionPoint));
    }

    public List<CriterionPointResponse> getAll(){
        var criterionPoints = criterionPointRepository.findAll();
        return criterionPoints.stream().map(criterionPointMapper::toCriterionPointResponse).toList();
    }

    public CriterionPointResponse update(CriterionPointUpdateRequest request , Long criterionPointId){
        CriterionPoint criterionPoint = criterionPointRepository.findById(criterionPointId)
                .orElseThrow(() -> new AppException(ErrorCode.CRITERION_NOT_EXISTED));
        criterionPointMapper.updateCriterionPoint(criterionPoint , request);

        return  criterionPointMapper.toCriterionPointResponse(criterionPointRepository.save(criterionPoint));
    }

    public void delete(Long criterionPointId){
        criterionPointRepository.findById(criterionPointId);
    }
}

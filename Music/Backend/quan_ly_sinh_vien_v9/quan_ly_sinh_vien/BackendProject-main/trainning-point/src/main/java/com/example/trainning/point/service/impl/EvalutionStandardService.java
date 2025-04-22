package com.example.trainning.point.service.impl;

import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.request.evalution.standard.EvalutionStandardRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.standard.EvalutionStandardResponse;
import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.EvalutionStandard;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.custom.EvalutionCategoryMapper;
import com.example.trainning.point.mapper.custom.EvalutionStandardMapper;
import com.example.trainning.point.repository.IEvalutionCategoryRepository;
import com.example.trainning.point.repository.IEvalutionStandardRepository;
import com.example.trainning.point.service.interfaces.IEvalutionCategoryService;
import com.example.trainning.point.service.interfaces.IEvalutionStandardService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EvalutionStandardService implements IEvalutionStandardService {
    IEvalutionStandardRepository evalutionStandardRepository;
    EvalutionStandardMapper evalutionStandardMapper;

    @Override
    public EvalutionStandard findEntityById(Long id) {
        var entity = evalutionStandardRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if (entity.getIsDelete() == true)
            throw new AppException(ErrorCode.NOT_FOUND);
        return entity;
    }

    @Override
    public EvalutionStandardResponse findById(Long id) {
        var entity = this.findEntityById(id);
        return evalutionStandardMapper.convertToResponse(entity);
    }

    @Override
    public List<EvalutionStandardResponse> findAll() {
        return evalutionStandardRepository.findAll().stream().map(it -> evalutionStandardMapper.convertToResponse(it)).toList();
    }

    @Override
    public EvalutionStandardResponse create(EvalutionStandardRequest request) {
        var entity = evalutionStandardMapper.convertToEntity(request);
        return evalutionStandardMapper.convertToResponse(evalutionStandardRepository.save(entity));
    }

    @Override
    public EvalutionStandardResponse update(Long id, EvalutionStandardRequest request) {
        var entity = this.findEntityById(id);

        entity.setName(request.getName());
        entity.setMinPoint(request.getMinPoint());
        entity.setMaxPoint(request.getMaxPoint());

        return evalutionStandardMapper.convertToResponse(evalutionStandardRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = this.findEntityById(id);
        entity.setIsDelete(true);
        evalutionStandardRepository.save(entity);
    }

}

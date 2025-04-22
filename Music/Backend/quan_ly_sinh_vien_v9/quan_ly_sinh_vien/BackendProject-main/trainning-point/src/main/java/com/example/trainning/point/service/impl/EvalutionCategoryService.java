package com.example.trainning.point.service.impl;

import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionResponse;
import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.EvalutionStandard;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.custom.EvalutionCategoryMapper;
import com.example.trainning.point.repository.IEvalutionCategoryRepository;
import com.example.trainning.point.service.interfaces.IEvalutionCategoryService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EvalutionCategoryService implements IEvalutionCategoryService {
    IEvalutionCategoryRepository evalutionCategoryRepository;
    EvalutionCategoryMapper evalutionCategoryMapper;

    @Override
    public EvalutionCategory findEntityById(Long id) {
        var entity = evalutionCategoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if (entity.getIsDelete() == true)
            throw new AppException(ErrorCode.NOT_FOUND);
        return entity;
    }

    @Override
    public EvalutionCategoryResponse findById(Long id) {
        var entity = this.findEntityById(id);
        return evalutionCategoryMapper.convertToResponse(entity);
    }

    @Override
    public List<EvalutionCategoryResponse> findAll() {
        return evalutionCategoryRepository.findAll().stream().map(it -> evalutionCategoryMapper.convertToResponse(it)).toList();
    }

    @Override
    public List<EvalutionResponse> findAllEvalutionResponse(String userId) {
        return evalutionCategoryRepository.findAll().stream().map(it -> evalutionCategoryMapper.convertToEvalutionResponse(it, userId)).toList();
    }

    @Override
    public EvalutionCategoryResponse create(EvalutionCategoryRequest request) {
        var entity = evalutionCategoryMapper.convertToEntity(request);
        return evalutionCategoryMapper.convertToResponse(evalutionCategoryRepository.save(entity));
    }

    @Override
    public EvalutionCategoryResponse update(Long id, EvalutionCategoryRequest request) {
        var entity = this.findEntityById(id);
        entity.setName(request.getName());
        entity.setMaxPoint(request.getMaxPoint());
        return evalutionCategoryMapper.convertToResponse(evalutionCategoryRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = this.findEntityById(id);
//        if(entity.getEvalutionStandardList() != null)
//            throw new AppException(ErrorCode.CANNOT_DELETE_CATEGORY_HAS_ANY_STANDARD);

        List<EvalutionStandard> evalutionStandardList = entity.getEvalutionStandardList();
        int cnt = 0;
        for(var it: evalutionStandardList){
            if(it.getIsDelete() == false) {
                log.error("name = " + it.getName());
                cnt++;
            }
        }
        if(cnt > 1)
            throw new AppException(ErrorCode.CANNOT_DELETE_CATEGORY_HAS_ANY_STANDARD);
        entity.setIsDelete(true);
        evalutionCategoryRepository.save(entity);
    }

}

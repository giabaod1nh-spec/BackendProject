package com.example.trainning.point.service.interfaces;

import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionResponse;
import com.example.trainning.point.entity.EvalutionCategory;

import java.util.List;

public interface IEvalutionCategoryService {
    EvalutionCategory findEntityById(Long id);
    EvalutionCategoryResponse findById(Long id);
    List<EvalutionCategoryResponse> findAll();
    List<EvalutionResponse> findAllEvalutionResponse(String userId);
    EvalutionCategoryResponse create(EvalutionCategoryRequest request);
    EvalutionCategoryResponse update(Long id, EvalutionCategoryRequest request);
    void delete(Long id);
}

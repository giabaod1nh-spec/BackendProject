package com.example.trainning.point.service.interfaces;

import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.request.evalution.standard.EvalutionStandardRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.standard.EvalutionStandardResponse;
import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.EvalutionStandard;

import java.util.List;

public interface IEvalutionStandardService {
    EvalutionStandard findEntityById(Long id);
    EvalutionStandardResponse findById(Long id);
    List<EvalutionStandardResponse> findAll();
    EvalutionStandardResponse create(EvalutionStandardRequest request);
    EvalutionStandardResponse update(Long id, EvalutionStandardRequest request);
    void delete(Long id);
}

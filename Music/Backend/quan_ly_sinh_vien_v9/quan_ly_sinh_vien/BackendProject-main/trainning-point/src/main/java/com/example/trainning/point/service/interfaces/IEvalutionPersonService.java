package com.example.trainning.point.service.interfaces;

import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.request.evalution.person.EvalutionPersonRequest;
import com.example.trainning.point.dto.request.evalution.person.MarkRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionPersonResponse;
import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.EvalutionPerson;

import java.util.List;

public interface IEvalutionPersonService {
    EvalutionPerson findEntityById(Long id);
    EvalutionPersonResponse findById(Long id);
    List<EvalutionPersonResponse> findAll();
    EvalutionPersonResponse create(EvalutionPersonRequest request);
    EvalutionPersonResponse update(Long id, EvalutionPersonRequest request);
    void delete(Long id);

    void studentMark(EvalutionPersonRequest request, Long id);
    void monitorMark(EvalutionPersonRequest request, Long id);
    void teacherMark(EvalutionPersonRequest request, Long id);

    Double getMarkOfSemester(Long idSemester, String idUser);
    String getRateOfSemester(Long idSemester, String idUser);
}

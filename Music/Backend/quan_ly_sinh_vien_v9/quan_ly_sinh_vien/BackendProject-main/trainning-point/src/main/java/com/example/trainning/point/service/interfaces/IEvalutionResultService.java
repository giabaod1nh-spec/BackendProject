package com.example.trainning.point.service.interfaces;

import com.example.trainning.point.dto.request.evalution.result.EvalutionResultRequest;
import com.example.trainning.point.dto.request.semester.SemesterRequest;
import com.example.trainning.point.dto.response.evalution.result.EvalutionResultResponse;
import com.example.trainning.point.dto.response.semester.SemesterResponse;
import com.example.trainning.point.entity.EvalutionResult;
import com.example.trainning.point.entity.Semester;
import com.example.trainning.point.enums.EvalutionStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEvalutionResultService {
    EvalutionResult findEntityById(Long id);
    EvalutionResultResponse findById(Long id);
    List<EvalutionResultResponse> findAll();
    EvalutionResultResponse create(EvalutionResultRequest request, String userId);
    EvalutionResultResponse update(Long id, EvalutionResultRequest request);
    void delete(Long id);

    EvalutionResult findBySemesterAndUser(Long idSemester, String userId);

}

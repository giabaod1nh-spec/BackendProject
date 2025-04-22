package com.example.trainning.point.service.impl;

import com.example.trainning.point.dto.request.evalution.result.EvalutionResultRequest;
import com.example.trainning.point.dto.request.semester.SemesterRequest;
import com.example.trainning.point.dto.response.evalution.result.EvalutionResultResponse;
import com.example.trainning.point.dto.response.semester.SemesterResponse;
import com.example.trainning.point.entity.EvalutionResult;
import com.example.trainning.point.entity.Semester;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.custom.EvalutionResultMapper;
import com.example.trainning.point.mapper.custom.SemesterMapper;
import com.example.trainning.point.repository.IEvalutionResultRepository;
import com.example.trainning.point.repository.ISemesterRepository;
import com.example.trainning.point.service.interfaces.IEvalutionResultService;
import com.example.trainning.point.service.interfaces.ISemesterService;
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
public class EvalutionResultService implements IEvalutionResultService {
    IEvalutionResultRepository evalutionResultRepository;
    EvalutionResultMapper resultMapper;

    @Override
    public EvalutionResult findEntityById(Long id) {
        var entity = evalutionResultRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if (entity.getIsDelete() == true)
            throw new AppException(ErrorCode.NOT_FOUND);
        return entity;
    }

    @Override
    public EvalutionResultResponse findById(Long id) {
        var entity = this.findEntityById(id);
        return resultMapper.convertToResponse(entity);
    }

    @Override
    public List<EvalutionResultResponse> findAll() {
        return evalutionResultRepository.findAll().stream().map(it -> resultMapper.convertToResponse(it)).toList();
    }

    @Override
    public EvalutionResultResponse create(EvalutionResultRequest request, String userId) {
        var entity = resultMapper.convertToEntity(request, userId);
        return resultMapper.convertToResponse(evalutionResultRepository.save(entity));
    }

    @Override
    public EvalutionResultResponse update(Long id, EvalutionResultRequest request) {
        var entity = this.findEntityById(id);
        entity.setStatus(request.getStatus());

        return resultMapper.convertToResponse(evalutionResultRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = this.findEntityById(id);
        entity.setIsDelete(true);
        evalutionResultRepository.save(entity);
    }

    @Override
    public EvalutionResult findBySemesterAndUser(Long idSemester, String userId) {
        return evalutionResultRepository.findBySemesterIdAndUserId(idSemester, userId);
    }
}

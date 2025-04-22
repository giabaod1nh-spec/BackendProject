package com.example.trainning.point.service.impl;

import com.example.trainning.point.configuration.LockMarkConfig;
import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.request.evalution.person.EvalutionPersonRequest;
import com.example.trainning.point.dto.request.evalution.person.MarkRequest;
import com.example.trainning.point.dto.request.evalution.result.EvalutionResultRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionPersonResponse;
import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.EvalutionPerson;
import com.example.trainning.point.entity.EvalutionResult;
import com.example.trainning.point.enums.EvalutionStatus;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.custom.EvalutionCategoryMapper;
import com.example.trainning.point.mapper.custom.EvalutionPersonMapper;
import com.example.trainning.point.repository.IEvalutionCategoryRepository;
import com.example.trainning.point.repository.IEvalutionPersonRepository;
import com.example.trainning.point.repository.IEvalutionResultRepository;
import com.example.trainning.point.service.interfaces.IEvalutionCategoryService;
import com.example.trainning.point.service.interfaces.IEvalutionPersonService;
import com.example.trainning.point.service.interfaces.IEvalutionResultService;
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
public class EvalutionPersonService implements IEvalutionPersonService {
    IEvalutionPersonRepository evalutionPersonRepository;
    EvalutionPersonMapper evalutionPersonMapper;
    IEvalutionResultService evalutionResultService;
    IEvalutionResultRepository evalutionResultRepository;
    LockMarkConfig lockMarkConfig;

    @Override
    public EvalutionPerson findEntityById(Long id) {
        var entity = evalutionPersonRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if (entity.getIsDelete() == true)
            throw new AppException(ErrorCode.NOT_FOUND);
        return entity;
    }

    @Override
    public EvalutionPersonResponse findById(Long id) {
        var entity = this.findEntityById(id);
        return evalutionPersonMapper.convertToResponse(entity);
    }

    @Override
    public List<EvalutionPersonResponse> findAll() {
        return evalutionPersonRepository.findAll().stream().map(it -> evalutionPersonMapper.convertToResponse(it)).toList();
    }

    @Override
    public EvalutionPersonResponse create(EvalutionPersonRequest request) {
        var entity = evalutionPersonMapper.convertToEntity(request);


        EvalutionResult evalutionResult = evalutionResultRepository
                .findByUser(entity.getUser().getUserId());



        if (evalutionResult == null) {
            log.error("Khong tim thay");
//            System.out.println(entity.getSemester().getId() + ". " + entity.getUser().getUserId());
            EvalutionResultRequest resultRequest = EvalutionResultRequest.builder()
                    .status(EvalutionStatus.WAIT_CONFIRMATION.name())
                    .semesterId(request.getSemesterId())
                    .build();
            evalutionResultService.create(resultRequest, request.getUserId());
        }

        return evalutionPersonMapper.convertToResponse(evalutionPersonRepository.save(entity));
    }

    @Override
    public EvalutionPersonResponse update(Long id, EvalutionPersonRequest request) {
        var entity = this.findEntityById(id);
//        entity.setMonitorScore(request.getMonitorScore());
        entity.setStudentScore(request.getStudentScore());
//        entity.setTeacherScore(request.getTeacherScore());
        return evalutionPersonMapper.convertToResponse(evalutionPersonRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = this.findEntityById(id);
        entity.setIsDelete(true);
        evalutionPersonRepository.save(entity);
    }

    @Override
    public void studentMark(EvalutionPersonRequest request, Long id) {
        var entity = this.findEntityById(id);

        int retryCount = 0;
        int maxRetries = 4;
        long retryDelay = 1000L;

        while (retryCount < maxRetries) {
            boolean isLock = lockMarkConfig.getLock(entity.getSemester().getId(), entity.getUser().getUserId());
            if (isLock == false)
                break;

            retryCount ++;
            log.error("retry cnt = " + retryCount);
            if (retryCount >= maxRetries)
                throw new AppException(ErrorCode.ANOTHER_IS_MARKING);

            try {
                Thread.sleep(retryDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
        lockMarkConfig.createLock(entity.getSemester().getId(), entity.getUser().getUserId());

        try {
            if (entity.getMonitorScore() != null || entity.getTeacherScore() != null)
                throw new AppException(ErrorCode.CANNOT_UPDATE_MARK);

            entity.setStudentScore(request.getStudentScore());
            evalutionPersonRepository.save(entity);

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                log.error("err sleep: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {

        }

        lockMarkConfig.unLock(entity.getSemester().getId(), entity.getUser().getUserId());
    }

    @Override
    public void monitorMark(EvalutionPersonRequest request, Long id) {
        try{
            if(id == null){
                this.create(request);
                return;
            }
        }catch (Exception e){

        }

        var entity = this.findEntityById(id);

        int retryCount = 0;
        int maxRetries = 10;
        long retryDelay = 1000L;

        while (retryCount < maxRetries) {
            boolean isLock = lockMarkConfig.getLock(entity.getSemester().getId(), entity.getUser().getUserId());
            if (isLock == false)
                break;

            retryCount ++;
            if (retryCount >= maxRetries)
                throw new AppException(ErrorCode.ANOTHER_IS_MARKING);

            try {
                Thread.sleep(retryDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
        lockMarkConfig.createLock(entity.getSemester().getId(), entity.getUser().getUserId());

        try {
            if (entity.getTeacherScore() != null)
                throw new AppException(ErrorCode.CANNOT_UPDATE_MARK);

            entity.setMonitorScore(request.getMonitorScore());
            evalutionPersonRepository.save(entity);
        } catch (Exception e) {

        }
        lockMarkConfig.unLock(entity.getSemester().getId(), entity.getUser().getUserId());
    }

    @Override
    public void teacherMark(EvalutionPersonRequest request, Long id) {
        var entity = this.findEntityById(id);

        int retryCount = 0;
        int maxRetries = 10;
        long retryDelay = 1000L;

        while (retryCount < maxRetries) {
            boolean isLock = lockMarkConfig.getLock(entity.getSemester().getId(), entity.getUser().getUserId());
            if (isLock == false)
                break;

            retryCount ++;
            if (retryCount >= maxRetries)
                throw new AppException(ErrorCode.ANOTHER_IS_MARKING);

            try {
                Thread.sleep(retryDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
        lockMarkConfig.createLock(entity.getSemester().getId(), entity.getUser().getUserId());

        try {
            entity.setTeacherScore(request.getTeacherScore());
            evalutionPersonRepository.save(entity);

            EvalutionResult evalutionResult = evalutionResultService
                    .findBySemesterAndUser(entity.getSemester().getId(), entity.getUser().getUserId());
            if (evalutionResult != null) {
                evalutionResult.setStatus(this.getRateOfSemester(entity.getSemester().getId(), entity.getUser().getUserId()));
                evalutionResultRepository.save(evalutionResult);
            }
        } catch (Exception e) {

        }

        lockMarkConfig.unLock(entity.getSemester().getId(), entity.getUser().getUserId());
    }

    @Override
    public Double getMarkOfSemester(Long idSemester, String idUser) {
        List<EvalutionPerson> evalutionPersonList = evalutionPersonRepository.findBySemesterIdAndUserUserId(idSemester, idUser);
        Double res = 0.0;
        for (var it : evalutionPersonList)
            if (it != null && it.getTeacherScore() != null)
                res += it.getTeacherScore();

        return res;
    }

    @Override
    public String getRateOfSemester(Long idSemester, String idUser) {
        Double mark = this.getMarkOfSemester(idSemester, idUser);
        String res = "";

        if (mark < 35)
            res = EvalutionStatus.POOR.name();
        else if (mark < 50)
            res = EvalutionStatus.BELOW_AVERAGE.name();
        else if (mark < 65)
            res = EvalutionStatus.AVERAGE.name();
        else if (mark < 80)
            res = EvalutionStatus.GOOD.name();
        else if (mark < 90)
            res = EvalutionStatus.VERY_GOOD.name();
        else if (mark <= 100)
            res = EvalutionStatus.EXCELLENT.name();

        return res;
    }
}

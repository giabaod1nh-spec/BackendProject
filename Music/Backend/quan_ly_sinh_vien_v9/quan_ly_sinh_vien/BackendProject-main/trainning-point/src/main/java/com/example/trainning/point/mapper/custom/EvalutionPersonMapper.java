package com.example.trainning.point.mapper.custom;

import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.request.evalution.person.EvalutionPersonRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionPersonResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionResponse;
import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.EvalutionPerson;
import com.example.trainning.point.entity.EvalutionStandard;
import com.example.trainning.point.entity.Semester;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.repository.IEvalutionStandardRepository;
import com.example.trainning.point.repository.ISemesterRepository;
import com.example.trainning.point.repository.UserRepository;
import com.example.trainning.point.service.interfaces.IEvalutionStandardService;
import com.example.trainning.point.service.interfaces.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EvalutionPersonMapper {
    private static final Logger log = LoggerFactory.getLogger(EvalutionPersonMapper.class);
    IEvalutionStandardRepository evalutionStandardRepository;
    IUserService userService;
    ISemesterRepository semesterRepository;
    private final UserRepository userRepository;

    public EvalutionPerson convertToEntity(EvalutionPersonRequest request){
        EvalutionStandard standard = evalutionStandardRepository
                .findById(request.getEvalutionStandardId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        var email = authentication.getName();
//        var user = userService.findByEmail(email);
        var user = userRepository.findById(request.getUserId()).orElse(null);
        log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.error("user = " + user.getUserId());
        Semester semester = semesterRepository.findById(request.getSemesterId()).orElseThrow(() ->
                new AppException(ErrorCode.SEMESTER_NOT_FOUND));

        return EvalutionPerson.builder()
                .studentScore(request.getStudentScore())
                .monitorScore(request.getMonitorScore())
                .teacherScore(request.getTeacherScore())
                .evalutionStandard(standard)
                .user(user)
                .semester(semester)
                .build();
    }

    public EvalutionPersonResponse convertToResponse(EvalutionPerson entity){

        if(entity == null || entity.getId() == null){
            return EvalutionPersonResponse.builder()
                    .id(null)
                    .studentScore(0.0)
                    .monitorScore(0.0)
                    .teacherScore(0.0)
                    .evalutionStandardId(null)
                    .userId(null)
                    .build();
        }

        Long evalutionStandardId = (entity.getEvalutionStandard() != null)
                ? entity.getEvalutionStandard().getId()
                : null;



        return EvalutionPersonResponse.builder()
                .id(entity.getId())
                .studentScore(entity.getStudentScore())
                .monitorScore(entity.getMonitorScore())
                .teacherScore(entity.getTeacherScore())
                .evalutionStandardId(evalutionStandardId)
                .userId(entity.getUser().getUserId())
                .build();
    }


}

package com.example.trainning.point.service.impl;

import com.example.trainning.point.dto.request.classes.ClassRequest;
import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.response.classes.ClassResponse;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.entity.ClassManager;
import com.example.trainning.point.entity.EvalutionCategory;
import com.example.trainning.point.entity.User;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.custom.ClassMapper;
import com.example.trainning.point.mapper.custom.EvalutionCategoryMapper;
import com.example.trainning.point.repository.IClassManagerRepository;
import com.example.trainning.point.repository.IEvalutionCategoryRepository;
import com.example.trainning.point.repository.UserRepository;
import com.example.trainning.point.service.interfaces.IClassManagerService;
import com.example.trainning.point.service.interfaces.IEvalutionCategoryService;
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
public class ClassService implements IClassManagerService {
    IClassManagerRepository classManagerRepository;
    ClassMapper classMapper;
    UserService userService;
    UserRepository userRepository;

    @Override
    public ClassManager findEntityById(Long id) {
        var entity = classManagerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if (entity.getIsDelete() == true)
            throw new AppException(ErrorCode.NOT_FOUND);
        return entity;
    }

    @Override
    public ClassResponse findById(Long id) {
        var entity = this.findEntityById(id);
        return classMapper.convertToResponse(entity);
    }

    @Override
    public List<ClassResponse> findAll() {
        return classManagerRepository.findAll().stream().map(it -> classMapper.convertToResponse(it)).toList();
    }

    @Override
    public ClassResponse create(ClassRequest request) {
        var entity = classMapper.convertToEntity(request);
        return classMapper.convertToResponse(classManagerRepository.save(entity));
    }

    @Override
    public ClassResponse update(Long id, ClassRequest request) {
        var entity = this.findEntityById(id);
        entity.setCodeClass(request.getCodeClass());
        entity.setName(request.getName());
        entity.setAcademicCohort(request.getAcademicCohort());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());

        return classMapper.convertToResponse(classManagerRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = this.findEntityById(id);
        entity.setIsDelete(true);
        classManagerRepository.save(entity);
    }

    @Override
    public void addStudent(List<String> idStudent, Long classId) {
        ClassManager classManager = this.findEntityById(classId);
        for (var it: idStudent){
            User user = userRepository.findById(it).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            user.setClassManager(classManager);
            userRepository.save(user);
        }
    }

}

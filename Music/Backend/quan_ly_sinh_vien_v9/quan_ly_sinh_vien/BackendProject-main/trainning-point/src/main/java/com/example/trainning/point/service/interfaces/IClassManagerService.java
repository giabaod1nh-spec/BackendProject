package com.example.trainning.point.service.interfaces;

import com.example.trainning.point.dto.request.classes.ClassRequest;
import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.response.classes.ClassResponse;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.entity.ClassManager;
import com.example.trainning.point.entity.EvalutionCategory;

import java.util.List;

public interface IClassManagerService {
    ClassManager findEntityById(Long id);
    ClassResponse findById(Long id);
    List<ClassResponse> findAll();
    ClassResponse create(ClassRequest request);
    ClassResponse update(Long id, ClassRequest request);
    void delete(Long id);
    void addStudent(List<String> idStudent, Long classId);
}

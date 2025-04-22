package com.example.trainning.point.service.interfaces;

import com.example.trainning.point.dto.request.classes.ClassRequest;
import com.example.trainning.point.dto.request.semester.SemesterRequest;
import com.example.trainning.point.dto.response.classes.ClassResponse;
import com.example.trainning.point.dto.response.semester.SemesterResponse;
import com.example.trainning.point.entity.ClassManager;
import com.example.trainning.point.entity.Semester;

import java.util.List;

public interface ISemesterService {
    Semester findEntityById(Long id);
    SemesterResponse findById(Long id);
    List<SemesterResponse> findAll();
    SemesterResponse create(SemesterRequest request);
    SemesterResponse update(Long id, SemesterRequest request);
    void delete(Long id);
}

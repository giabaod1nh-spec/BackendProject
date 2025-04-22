package com.example.trainning.point.service.impl;

import com.example.trainning.point.dto.request.semester.SemesterRequest;
import com.example.trainning.point.dto.response.semester.SemesterResponse;
import com.example.trainning.point.entity.Semester;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.custom.SemesterMapper;
import com.example.trainning.point.repository.ISemesterRepository;
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
public class SemesterService implements ISemesterService {
    ISemesterRepository semesterRepository;
    SemesterMapper semesterMapper;

    @Override
    public Semester findEntityById(Long id) {
        var entity = semesterRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        if (entity.getIsDelete() == true)
            throw new AppException(ErrorCode.NOT_FOUND);
        return entity;
    }

    @Override
    public SemesterResponse findById(Long id) {
        var entity = this.findEntityById(id);
        return semesterMapper.convertToResponse(entity);
    }

    @Override
    public List<SemesterResponse> findAll() {
        return semesterRepository.findAll().stream().map(it -> semesterMapper.convertToResponse(it)).toList();
    }

    @Override
    public SemesterResponse create(SemesterRequest request) {
        var entity = semesterMapper.convertToEntity(request);
        return semesterMapper.convertToResponse(semesterRepository.save(entity));
    }

    @Override
    public SemesterResponse update(Long id, SemesterRequest request) {
        var entity = this.findEntityById(id);
        entity.setName(request.getName());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());

        return semesterMapper.convertToResponse(semesterRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = this.findEntityById(id);
        entity.setIsDelete(true);
        semesterRepository.save(entity);
    }
}

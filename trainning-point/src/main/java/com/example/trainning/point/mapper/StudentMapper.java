package com.example.trainning.point.mapper;

import com.example.trainning.point.dto.request.StudentCreationRequest;
import com.example.trainning.point.dto.request.StudentUpdateRequest;
import com.example.trainning.point.dto.response.StudentResponse;
import com.example.trainning.point.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {
  //  @Mapping(target = "user" , ignore = true)
    Student toStudent(StudentCreationRequest request);

    StudentResponse toStudentResponse(Student student);

    void updateStudent(@MappingTarget Student student , StudentUpdateRequest request);
}

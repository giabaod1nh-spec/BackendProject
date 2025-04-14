package com.example.trainning.point.mapper;

import com.example.trainning.point.dto.request.InstructorCreationRequest;
import com.example.trainning.point.dto.request.InstructorUpdateRequest;
import com.example.trainning.point.dto.request.StudentCreationRequest;
import com.example.trainning.point.dto.request.StudentUpdateRequest;
import com.example.trainning.point.dto.response.InstructorResponse;
import com.example.trainning.point.dto.response.StudentResponse;
import com.example.trainning.point.entity.Instructor;
import com.example.trainning.point.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InstructorMapper {
  //  @Mapping(target = "user" , ignore = true)
    Instructor toInstructor(InstructorCreationRequest request);

    InstructorResponse toInstructorResponse(Instructor instructor);

    void updateInstructor(@MappingTarget Instructor instructor , InstructorUpdateRequest request);
}

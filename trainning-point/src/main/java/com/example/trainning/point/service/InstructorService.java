package com.example.trainning.point.service;

import com.example.trainning.point.dto.request.InstructorCreationRequest;
import com.example.trainning.point.dto.request.InstructorUpdateRequest;
import com.example.trainning.point.dto.request.StudentCreationRequest;
import com.example.trainning.point.dto.request.StudentUpdateRequest;
import com.example.trainning.point.dto.response.InstructorResponse;
import com.example.trainning.point.dto.response.StudentResponse;
import com.example.trainning.point.entity.Instructor;
import com.example.trainning.point.entity.Student;
import com.example.trainning.point.entity.User;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.InstructorMapper;
import com.example.trainning.point.mapper.StudentMapper;
import com.example.trainning.point.repository.InstructorRepository;
import com.example.trainning.point.repository.RoleRepository;
import com.example.trainning.point.repository.StudentRepository;
import com.example.trainning.point.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@Service
public class InstructorService {
    InstructorRepository instructorRepository;
    UserRepository userRepository;
    InstructorMapper instructorMapper;
    RoleRepository roleRepository;

    public InstructorResponse createInstructor(InstructorCreationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

      // if (userRepository.existsByUserId(request.getUserId())){
        //   throw new AppException(ErrorCode.OPERATION_NOT_ALLOWED);
      // }
         if(instructorRepository.existsByUser(user)){
         throw new AppException(ErrorCode.STUDENT_EXISTED);
         }
        Instructor instructor = instructorMapper.toInstructor(request);
        instructor.setUser(user);
        return instructorMapper.toInstructorResponse(instructorRepository.save(instructor));
    }
    public InstructorResponse getInstructor(@PathVariable Long instructorId){
        return instructorMapper.toInstructorResponse(instructorRepository.findByInstructorId(instructorId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND) ));
    }

    public List<InstructorResponse> getAllInstructor(){
        return instructorRepository.findAll().stream()
                .map(instructorMapper::toInstructorResponse).toList();
    }

    public InstructorResponse updateInstructor(@PathVariable Long instructorId , InstructorUpdateRequest request){
        Instructor instructor = instructorRepository.findByInstructorId(instructorId).orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
        instructorMapper.updateInstructor(instructor , request);

        return instructorMapper.toInstructorResponse(instructorRepository.save(instructor));
    }

}


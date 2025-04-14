package com.example.trainning.point.service;

import com.example.trainning.point.dto.request.StudentCreationRequest;
import com.example.trainning.point.dto.request.StudentUpdateRequest;
import com.example.trainning.point.dto.response.StudentResponse;
import com.example.trainning.point.entity.Student;
import com.example.trainning.point.entity.User;
import com.example.trainning.point.exception.AppException;
import com.example.trainning.point.exception.ErrorCode;
import com.example.trainning.point.mapper.StudentMapper;
import com.example.trainning.point.repository.RoleRepository;
import com.example.trainning.point.repository.StudentRepository;
import com.example.trainning.point.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@Service
public class StudentService {
    StudentRepository studentRepository;
    UserRepository userRepository;
    StudentMapper studentMapper;
    RoleRepository roleRepository;

    public StudentResponse createStudent(StudentCreationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

      // if (userRepository.existsByUserId(request.getUserId())){
        //   throw new AppException(ErrorCode.OPERATION_NOT_ALLOWED);
      // }
         if(studentRepository.existsByUser(user)){
         throw new AppException(ErrorCode.STUDENT_EXISTED);
         }
        Student student = studentMapper.toStudent(request);
        student.setUser(user);
        return studentMapper.toStudentResponse(studentRepository.save(student));
    }
    public StudentResponse getStudent(@PathVariable Long studentId){
        return studentMapper.toStudentResponse(studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND) ));
    }

    public List<StudentResponse> getAllStudent(){
        return studentRepository.findAll().stream()
                .map(studentMapper::toStudentResponse).toList();
    }

    public StudentResponse updateStudent(@PathVariable Long studentId , StudentUpdateRequest request){
        Student student = studentRepository.findByStudentId(studentId).orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
        studentMapper.updateStudent(student , request);

        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

}


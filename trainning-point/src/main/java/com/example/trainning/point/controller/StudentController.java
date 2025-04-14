package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.ApiResponse;
import com.example.trainning.point.dto.request.StudentCreationRequest;
import com.example.trainning.point.dto.request.StudentUpdateRequest;
import com.example.trainning.point.dto.response.StudentResponse;
import com.example.trainning.point.service.StudentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class StudentController {
     StudentService studentService;

     @PostMapping
    ApiResponse<StudentResponse> createStudent(@RequestBody StudentCreationRequest request){
                   return ApiResponse.<StudentResponse>builder()
                           .result(studentService.createStudent(request))
                           .build();
     }

     @GetMapping("/{studentId}")
    ApiResponse<StudentResponse> getStudent(@PathVariable("studentId") Long studentId){
          return ApiResponse.<StudentResponse>builder()
                  .result(studentService.getStudent(studentId))
                  .build();
     }

     @GetMapping
    ApiResponse<List<StudentResponse>> getAllStudent(){
         return ApiResponse.<List<StudentResponse>>builder()
                 .result(studentService.getAllStudent())
                 .build();
     }

     @PutMapping("/{studentId}")
    ApiResponse<StudentResponse> updateStudent(@PathVariable("studentId") Long studentId , @RequestBody StudentUpdateRequest request){
         return  ApiResponse.<StudentResponse>builder()
                 .result(studentService.updateStudent(studentId , request))
                 .build();
     }
}

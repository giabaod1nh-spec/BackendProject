package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.*;
import com.example.trainning.point.dto.response.InstructorResponse;
import com.example.trainning.point.dto.response.StudentResponse;
import com.example.trainning.point.service.InstructorService;
import com.example.trainning.point.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class InstructorController {
        InstructorService instructorService;

     @PostMapping
    ApiResponse<InstructorResponse> createInstructor(@RequestBody InstructorCreationRequest request){
                   return ApiResponse.<InstructorResponse>builder()
                           .result(instructorService.createInstructor(request))
                           .build();
     }

     @GetMapping("/{instructorId}")
    ApiResponse<InstructorResponse> getInstructor(@PathVariable("instructorId") Long instructorId){
          return ApiResponse.<InstructorResponse>builder()
                  .result(instructorService.getInstructor(instructorId))
                  .build();
     }

     @GetMapping
    ApiResponse<List<InstructorResponse>> getAllStudent(){
         return ApiResponse.<List<InstructorResponse>>builder()
                 .result(instructorService.getAllInstructor())
                 .build();
     }

     @PutMapping("/{studentId}")
    ApiResponse<InstructorResponse> updateStudent(@PathVariable("instructorId") Long instructorId , @RequestBody InstructorUpdateRequest request){
         return  ApiResponse.<InstructorResponse>builder()
                 .result(instructorService.updateInstructor(instructorId , request))
                 .build();
     }
}

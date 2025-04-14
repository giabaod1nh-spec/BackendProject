package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.*;
import com.example.trainning.point.dto.response.CriterionResponse;
import com.example.trainning.point.dto.response.InstructorResponse;
import com.example.trainning.point.service.CriterionService;
import com.example.trainning.point.service.InstructorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/criterions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class CriterionController {
        CriterionService criterionService;

     @PostMapping
    ApiResponse<CriterionResponse> createCriterion(@RequestBody CriterionCreationRequest request){
                   return ApiResponse.<CriterionResponse>builder()
                           .result(criterionService.createCriterion(request))
                           .build();
     }

     @GetMapping("/{criterionId}")
    ApiResponse<CriterionResponse> getInstructor(@PathVariable("instructorId") Long criterionId){
          return ApiResponse.<CriterionResponse>builder()
                  .result(criterionService.getCriterion(criterionId))
                  .build();
     }

     @GetMapping
    ApiResponse<List<CriterionResponse>> getAllCriterion(){
         return ApiResponse.<List<CriterionResponse>>builder()
                 .result(criterionService.getAllCriterion())
                 .build();
     }

     @PutMapping("/{criterionId}")
    ApiResponse<CriterionResponse> updateStudent(@PathVariable("criterionId") Long criterionId , @RequestBody CriterionUpdateRequest request){
         return  ApiResponse.<CriterionResponse>builder()
                 .result(criterionService.updateCriterion(criterionId , request))
                 .build();
     }
}

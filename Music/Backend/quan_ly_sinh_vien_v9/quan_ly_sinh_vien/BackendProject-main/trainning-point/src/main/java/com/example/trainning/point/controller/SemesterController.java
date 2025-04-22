package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.ApiResponse;
import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.request.semester.SemesterRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionResponse;
import com.example.trainning.point.dto.response.semester.SemesterResponse;
import com.example.trainning.point.service.impl.EvalutionCategoryService;
import com.example.trainning.point.service.interfaces.ISemesterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semester")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class SemesterController {
    ISemesterService semesterService;

    @Operation(summary = "Get all semester")
    @GetMapping("/list")
    ApiResponse<List<SemesterResponse>> findAll(){
        return ApiResponse.<List<SemesterResponse>>builder()
                .result(semesterService.findAll())
                .build();
    }

    @Operation(summary = "Get semester by id")
    @GetMapping("/find")
    ApiResponse<SemesterResponse> findById(@RequestParam(name = "id") Long id){
        return ApiResponse.<SemesterResponse>builder()
                .result(semesterService.findById(id))
                .build();
    }

    @Operation(summary = "create semester ")
    @PostMapping("/create")
    ApiResponse<SemesterResponse> createUserCustom(@RequestBody SemesterRequest request){
        return ApiResponse.<SemesterResponse>builder()
                .result(semesterService.create(request))
                .build();
    }

    @Operation(summary = "update semester ")
    @PutMapping("/update/{id}")
    ApiResponse<SemesterResponse> updateUserCustom(@PathVariable("id") Long id , @RequestBody SemesterRequest request){
        return ApiResponse.<SemesterResponse>builder()
                .result(semesterService.update(id , request))
                .build();
    }

    @Operation(summary = "delete semester")
    @DeleteMapping("/delete/{id}")
    ApiResponse<Boolean> updatePassCustom(@PathVariable Long id){
        semesterService.delete(id);
        return ApiResponse.<Boolean>builder()
                .result(true)
                .build();
    }
}

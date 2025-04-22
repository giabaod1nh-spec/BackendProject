package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.ApiResponse;
import com.example.trainning.point.dto.request.classes.ClassRequest;
import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.response.classes.ClassResponse;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.service.impl.EvalutionCategoryService;
import com.example.trainning.point.service.interfaces.IClassManagerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class ClassController {
    IClassManagerService classManagerService;

    @Operation(summary = "Get all class")
    @GetMapping("/list")
    ApiResponse<List<ClassResponse>> findAll(){
        return ApiResponse.<List<ClassResponse>>builder()
                .result(classManagerService.findAll())
                .build();
    }

    @Operation(summary = "Get class by id")
    @GetMapping("/find")
    ApiResponse<ClassResponse> findById(@RequestParam(name = "id") Long id){
        return ApiResponse.<ClassResponse>builder()
                .result(classManagerService.findById(id))
                .build();
    }

    @Operation(summary = "create class")
    @PostMapping("/create")
    ApiResponse<ClassResponse> createUserCustom(@RequestBody ClassRequest request){
        return ApiResponse.<ClassResponse>builder()
                .result(classManagerService.create(request))
                .build();
    }

    @Operation(summary = "update class")
    @PutMapping("/update/{id}")
    ApiResponse<ClassResponse> updateUserCustom(@PathVariable("id") Long id , @RequestBody ClassRequest request){
        return ApiResponse.<ClassResponse>builder()
                .result(classManagerService.update(id , request))
                .build();
    }

    @Operation(summary = "delete class")
    @DeleteMapping("/delete/{id}")
    ApiResponse<Boolean> updatePassCustom(@PathVariable Long id){
        classManagerService.delete(id);
        return ApiResponse.<Boolean>builder()
                .result(true)
                .build();
    }
}

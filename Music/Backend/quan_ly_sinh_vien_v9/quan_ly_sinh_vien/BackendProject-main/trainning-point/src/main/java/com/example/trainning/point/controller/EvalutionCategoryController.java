package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.ApiResponse;
import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.request.user.UserRequest;
import com.example.trainning.point.dto.request.user.UserSearch;
import com.example.trainning.point.dto.response.UserResponse;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.person.EvalutionResponse;
import com.example.trainning.point.service.impl.EvalutionCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evalution/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class EvalutionCategoryController {
    EvalutionCategoryService evalutionCategoryService;

    @Operation(summary = "Get all category")
    @GetMapping("/list")
    ApiResponse<List<EvalutionCategoryResponse>> findAll(){
        return ApiResponse.<List<EvalutionCategoryResponse>>builder()
                .result(evalutionCategoryService.findAll())
                .build();
    }

    @Operation(summary = "Get all ressponse")
    @GetMapping("/list-response")
    ApiResponse<List<EvalutionResponse>> findAllEvalutionResponse(@RequestParam(name = "userId", required = false) String userId){
        return ApiResponse.<List<EvalutionResponse>>builder()
                .result(evalutionCategoryService.findAllEvalutionResponse(userId))
                .build();
    }

    @Operation(summary = "Get category by id")
    @GetMapping("/find")
    ApiResponse<EvalutionCategoryResponse> findById(@RequestParam(name = "id") Long id){
        return ApiResponse.<EvalutionCategoryResponse>builder()
                .result(evalutionCategoryService.findById(id))
                .build();
    }

    @Operation(summary = "create evalution category")
    @PostMapping("/create")
    ApiResponse<EvalutionCategoryResponse> createUserCustom(@RequestBody EvalutionCategoryRequest request){
        return ApiResponse.<EvalutionCategoryResponse>builder()
                .result(evalutionCategoryService.create(request))
                .build();
    }

    @Operation(summary = "update evalution category")
    @PutMapping("/update/{id}")
    ApiResponse<EvalutionCategoryResponse> updateUserCustom(@PathVariable("id") Long id , @RequestBody EvalutionCategoryRequest request){
        return ApiResponse.<EvalutionCategoryResponse>builder()
                .result(evalutionCategoryService.update(id , request))
                .build();
    }

    @Operation(summary = "delete evalution category")
    @DeleteMapping("/delete/{id}")
    ApiResponse<Boolean> updatePassCustom(@PathVariable Long id){
        evalutionCategoryService.delete(id);
        return ApiResponse.<Boolean>builder()
                .result(true)
                .build();
    }
}

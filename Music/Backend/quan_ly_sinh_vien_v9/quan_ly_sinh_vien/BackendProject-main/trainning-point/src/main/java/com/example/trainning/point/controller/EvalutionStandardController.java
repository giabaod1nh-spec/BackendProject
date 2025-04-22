package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.ApiResponse;
import com.example.trainning.point.dto.request.evalution.category.EvalutionCategoryRequest;
import com.example.trainning.point.dto.request.evalution.standard.EvalutionStandardRequest;
import com.example.trainning.point.dto.response.evalution.category.EvalutionCategoryResponse;
import com.example.trainning.point.dto.response.evalution.standard.EvalutionStandardResponse;
import com.example.trainning.point.service.impl.EvalutionCategoryService;
import com.example.trainning.point.service.impl.EvalutionStandardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evalution/standard")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class EvalutionStandardController {
    EvalutionStandardService evalutionStandardService;

    @Operation(summary = "Get all standard")
    @GetMapping("/list")
    ApiResponse<List<EvalutionStandardResponse>> findAll(){
        return ApiResponse.<List<EvalutionStandardResponse>>builder()
                .result(evalutionStandardService.findAll())
                .build();
    }

    @Operation(summary = "Get standard by id")
    @GetMapping("/find")
    ApiResponse<EvalutionStandardResponse> findById(@RequestParam(name = "id") Long id){
        return ApiResponse.<EvalutionStandardResponse>builder()
                .result(evalutionStandardService.findById(id))
                .build();
    }

    @Operation(summary = "create standard category")
    @PostMapping("/create")
    ApiResponse<EvalutionStandardResponse> createUserCustom(@RequestBody EvalutionStandardRequest request){
        return ApiResponse.<EvalutionStandardResponse>builder()
                .result(evalutionStandardService.create(request))
                .build();
    }

    @Operation(summary = "update standard ")
    @PutMapping("/update/{id}")
    ApiResponse<EvalutionStandardResponse> updateUserCustom(@PathVariable("id") Long id , @RequestBody EvalutionStandardRequest request){
        return ApiResponse.<EvalutionStandardResponse>builder()
                .result(evalutionStandardService.update(id , request))
                .build();
    }

    @Operation(summary = "delete standard")
    @DeleteMapping("/delete/{id}")
    ApiResponse<Boolean> updatePassCustom(@PathVariable Long id){
        evalutionStandardService.delete(id);
        return ApiResponse.<Boolean>builder()
                .result(true)
                .build();
    }
}

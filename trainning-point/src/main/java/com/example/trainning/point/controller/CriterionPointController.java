package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.*;
import com.example.trainning.point.dto.response.CriterionPointResponse;
import com.example.trainning.point.dto.response.InstructorResponse;
import com.example.trainning.point.dto.response.PermissionResponse;
import com.example.trainning.point.service.CriterionPointService;
import com.example.trainning.point.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/criterionPoints")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class CriterionPointController {
    CriterionPointService criterionPointService;

    @PostMapping
    ApiResponse<CriterionPointResponse>create(@RequestBody CriterionPointCreationRequest request){
        return ApiResponse.<CriterionPointResponse>builder()
                .result(criterionPointService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<CriterionPointResponse>>getAll(){
        return ApiResponse.<List<CriterionPointResponse>>builder()
                .result(criterionPointService.getAll())
                .build();
    }

    @PutMapping("{criterionPointId}")
    ApiResponse<CriterionPointResponse> updateCriterionPoint(@RequestBody CriterionPointUpdateRequest request , @PathVariable Long criterionPointId) {
        return ApiResponse.<CriterionPointResponse>builder()
                .result(criterionPointService.update(request, criterionPointId))
                .build();
    }

    @DeleteMapping("/{criterionPointId}")
    ApiResponse<Void> delete(@PathVariable Long criterionPointId){
        criterionPointService.delete(criterionPointId);
        return ApiResponse.<Void>builder()
                .build();
    }
}

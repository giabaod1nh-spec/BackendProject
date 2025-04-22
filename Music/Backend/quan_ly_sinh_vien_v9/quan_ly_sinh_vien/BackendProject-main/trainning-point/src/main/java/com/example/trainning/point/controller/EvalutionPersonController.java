package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.ApiResponse;
import com.example.trainning.point.dto.request.evalution.person.EvalutionPersonRequest;
import com.example.trainning.point.dto.request.evalution.person.MarkRequest;
import com.example.trainning.point.dto.request.evalution.standard.EvalutionStandardRequest;
import com.example.trainning.point.dto.response.evalution.person.EvalutionPersonResponse;
import com.example.trainning.point.dto.response.evalution.standard.EvalutionStandardResponse;
import com.example.trainning.point.entity.EvalutionPerson;
import com.example.trainning.point.service.impl.EvalutionPersonService;
import com.example.trainning.point.service.impl.EvalutionStandardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evalution/person")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EvalutionPersonController {
    EvalutionPersonService evalutionPersonService;

    @Operation(summary = "Get all person")
    @GetMapping("/list")
    ApiResponse<List<EvalutionPersonResponse>> findAll() {
        return ApiResponse.<List<EvalutionPersonResponse>>builder()
                .result(evalutionPersonService.findAll())
                .build();
    }

    @Operation(summary = "Get person by id")
    @GetMapping("/find")
    ApiResponse<EvalutionPersonResponse> findById(@RequestParam(name = "id") Long id) {
        return ApiResponse.<EvalutionPersonResponse>builder()
                .result(evalutionPersonService.findById(id))
                .build();
    }

    @Operation(summary = "create person ")
    @PostMapping("/create")
    ApiResponse<EvalutionPersonResponse> createUserCustom(@RequestBody EvalutionPersonRequest request) {
        return ApiResponse.<EvalutionPersonResponse>builder()
                .result(evalutionPersonService.create(request))
                .build();
    }

    @Operation(summary = "student remark ")
    @PutMapping("/student-mark/{id}")
    ApiResponse<String> studentMark(@PathVariable Long id,
                                         @RequestBody EvalutionPersonRequest request) {
        evalutionPersonService.studentMark(request, id);
        return ApiResponse.<String>builder()
                .result("Ok")
                .build();
    }

    @PreAuthorize("hasAnyRole('COMMITTEE')")
    @Operation(summary = "monitor remark ")
    @PutMapping("/monitor-mark/{id}")
    ApiResponse<String> monitorMark(@PathVariable Long id,
                                    @RequestBody EvalutionPersonRequest request) {
        evalutionPersonService.monitorMark(request, id);
        return ApiResponse.<String>builder()
                .result("Ok")
                .build();
    }

    @PreAuthorize("hasAnyRole('COUNSELOR')")
    @Operation(summary = "teacher remark ")
    @PutMapping("/teacher-mark/{id}")
    ApiResponse<String> teacherMark(@PathVariable Long id,
                                    @RequestBody EvalutionPersonRequest request) {
        evalutionPersonService.teacherMark(request, id);
        return ApiResponse.<String>builder()
                .result("Ok")
                .build();
    }

//    @Operation(summary = "update person ")
//    @PutMapping("/update/{id}")
//    ApiResponse<EvalutionPersonResponse> updateUserCustom(@PathVariable("id") Long id , @RequestBody EvalutionPersonRequest request){
//        return ApiResponse.<EvalutionPersonResponse>builder()
//                .result(evalutionPersonService.update(id , request))
//                .build();
//    }

    @Operation(summary = "delete person")
    @DeleteMapping("/delete/{id}")
    ApiResponse<Boolean> updatePassCustom(@PathVariable Long id) {
        evalutionPersonService.delete(id);
        return ApiResponse.<Boolean>builder()
                .result(true)
                .build();
    }
}

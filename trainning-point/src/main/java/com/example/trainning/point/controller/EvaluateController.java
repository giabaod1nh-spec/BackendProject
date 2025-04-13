package com.example.trainning.point.controller;

import com.example.trainning.point.dto.request.EvaluateRequest;
import com.example.trainning.point.dto.response.EvaluateResponse;
import com.example.trainning.point.entity.Evaluate;
import com.example.trainning.point.service.EvaluateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluate")
@RequiredArgsConstructor
public class EvaluateController {

    private final EvaluateService evaluateService;

    // Tạo đánh giá mới
    @PostMapping
    public ResponseEntity<Evaluate> createEvaluate(@RequestBody EvaluateRequest request) {
        Evaluate savedEvaluate = evaluateService.createEvaluate(request);
        return ResponseEntity.ok(savedEvaluate);
    }

    // Lấy chi tiết 1 đánh giá
    @GetMapping("/{evaluateId}")
    public ResponseEntity<EvaluateResponse> getEvaluateById(@PathVariable Long evaluateId) {
        EvaluateResponse response = evaluateService.getEvaluateById(evaluateId);
        return ResponseEntity.ok(response);
    }

    // Lấy tất cả đánh giá theo sinh viên
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EvaluateResponse>> getEvaluatesByStudent(@PathVariable Long studentId) {
        List<EvaluateResponse> responses = evaluateService.getEvaluatesByStudent(studentId);
        return ResponseEntity.ok(responses);
    }
}

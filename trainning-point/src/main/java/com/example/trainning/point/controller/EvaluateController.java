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

    // 1. Tạo đánh giá mới
    @PostMapping
    public ResponseEntity<Evaluate> createEvaluate(@RequestBody EvaluateRequest request) {
        Evaluate savedEvaluate = evaluateService.createEvaluate(request);
        return ResponseEntity.ok(savedEvaluate);
    }

    // 2. Lấy chi tiết 1 đánh giá (theo ID)
    @GetMapping("/{evaluateId}")
    public ResponseEntity<EvaluateResponse> getEvaluateById(@PathVariable Long evaluateId) {
        EvaluateResponse response = evaluateService.getEvaluateById(evaluateId);
        return ResponseEntity.ok(response);
    }

    // 3. Lấy tất cả đánh giá của 1 sinh viên
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EvaluateResponse>> getEvaluatesByStudent(@PathVariable Long studentId) {
        List<EvaluateResponse> responses = evaluateService.getEvaluatesByStudent(studentId);
        return ResponseEntity.ok(responses);
    }

    // 4. Lấy toàn bộ đánh giá (admin xem)
    @GetMapping
    public ResponseEntity<List<Evaluate>> getAllEvaluates() {
        return ResponseEntity.ok(evaluateService.getAllEvaluates());
    }

    // 5. Cập nhật đánh giá
    @PutMapping("/{id}")
    public ResponseEntity<Evaluate> updateEvaluate(@PathVariable Long id, @RequestBody Evaluate updatedEvaluate) {
        Evaluate result = evaluateService.updateEvaluate(id, updatedEvaluate);
        return ResponseEntity.ok(result);
    }

    // 6. Xóa đánh giá
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluate(@PathVariable Long id) {
        evaluateService.deleteEvaluate(id);
        return ResponseEntity.noContent().build();
    }
}

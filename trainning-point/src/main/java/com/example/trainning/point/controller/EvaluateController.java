package com.example.trainning.point.controller;

import com.example.trainning.point.entity.Evaluate;
import com.example.trainning.point.service.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluate")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;

    @PostMapping
    public ResponseEntity<Evaluate> createEvaluate(@RequestBody Evaluate evaluate) {
        Evaluate savedEvaluate = evaluateService.createEvaluate(evaluate);
        return ResponseEntity.ok(savedEvaluate);
    }
}

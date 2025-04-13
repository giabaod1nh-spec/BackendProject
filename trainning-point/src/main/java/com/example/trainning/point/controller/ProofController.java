package com.example.trainning.point.controller;

import com.example.trainning.point.entity.Proof;
import com.example.trainning.point.service.ProofService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/proofs")
@RequiredArgsConstructor
public class ProofController {

    private final ProofService proofService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadProof(
            @RequestParam("file") MultipartFile file,
            @RequestParam("detailPointId") Long detailPointId
    ) {
        Proof proof = proofService.uploadProof(file, detailPointId);

        return ResponseEntity.ok(Map.of(
                "message", "Upload thành công",
                "fileUrl", proof.getFileUrl(),
                "status", proof.getStatus()
        ));
    }

    // === HANDLER LỖI TOÀN CỤC TRONG CONTROLLER NÀY ===

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Dữ liệu không hợp lệ",
                        "details", ex.getMessage()
                ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleInternalError(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Lỗi nội bộ máy chủ",
                        "details", ex.getMessage()
                ));
    }
}

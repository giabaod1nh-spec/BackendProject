package com.example.trainning.point.service;

import com.example.trainning.point.entity.DetailPoint;
import com.example.trainning.point.entity.Proof;
import com.example.trainning.point.repository.DetailPointRepository;
import com.example.trainning.point.repository.ProofRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProofService {

    private final ProofRepository proofRepository;
    private final DetailPointRepository detailPointRepository;

    public Proof uploadProof(MultipartFile file, Long detailPointId) {
        try {
            // Kiểm tra DetailPoint có tồn tại không
            DetailPoint detailPoint = detailPointRepository.findById(detailPointId)
                    .orElseThrow(() -> new IllegalArgumentException("DetailPoint ID không tồn tại: " + detailPointId));

            // Tạo tên file ngẫu nhiên và lưu file vào thư mục uploads
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get("uploads", fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Tạo đối tượng proof để lưu DB
            Proof proof = new Proof();
            proof.setFileUrl(path.toString().replace("\\", "/"));
            proof.setProofType("ACTIVITY");
            proof.setStatus("PENDING");
            proof.setDetailPoint(detailPoint);

            return proofRepository.save(proof);

        } catch (IOException e) {
            log.error("Lỗi khi upload file:", e);
            throw new RuntimeException("Không thể lưu file lên hệ thống", e);
        } catch (IllegalArgumentException e) {
            log.warn("Lỗi dữ liệu đầu vào:", e);
            throw e;
        } catch (Exception e) {
            log.error("Lỗi không xác định khi upload minh chứng:", e);
            throw new RuntimeException("Lỗi không mong muốn xảy ra", e);
        }
    }
}

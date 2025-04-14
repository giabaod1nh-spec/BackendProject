package com.example.trainning.point.service;

import com.example.trainning.point.dto.request.EvaluateRequest;
import com.example.trainning.point.dto.response.EvaluateResponse;
import com.example.trainning.point.entity.*;
import com.example.trainning.point.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluateService {

    private final EvaluateRepository evaluateRepository;
    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;
    private final CriterionRepository criterionRepository;
    private final DetailRepository detailRepository;

    public Evaluate createEvaluate(EvaluateRequest request) {
        try {
            Student student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("❌ Student not found"));

            Semester semester = semesterRepository.findById(request.getSemesterId())
                    .orElseThrow(() -> new RuntimeException("❌ Semester not found"));

            Evaluate evaluate = new Evaluate();
            evaluate.setStudent(student);
            evaluate.setSemester(semester);

            AtomicInteger totalScore = new AtomicInteger(0);

            List<CriterionPoint> criterionPoints = request.getCriterionPoints().stream().map(cpReq -> {
                Criterion criterion = criterionRepository.findById(cpReq.getCriterionId())
                        .orElseThrow(() -> new RuntimeException("❌ Criterion not found"));

                int criterionScore = validateAndConvertScore(cpReq.getScore());

                CriterionPoint criterionPoint = new CriterionPoint();
                criterionPoint.setEvaluate(evaluate);
                criterionPoint.setCriterion(criterion);
                criterionPoint.setScore(criterionScore);

                List<DetailPoint> detailPoints = cpReq.getDetailPoints().stream().map(dpReq -> {
                    Detail detail = detailRepository.findById(dpReq.getDetailId())
                            .orElseThrow(() -> new RuntimeException("❌ Detail not found"));

                    int detailScore = validateAndConvertScore(dpReq.getScore());

                    DetailPoint detailPoint = new DetailPoint();
                    detailPoint.setCriterionPoint(criterionPoint);
                    detailPoint.setDetail(detail);
                    detailPoint.setScore(detailScore);

                    return detailPoint;
                }).collect(Collectors.toList());

                criterionPoint.setDetailPoints(detailPoints);
                totalScore.addAndGet(criterionPoint.getScore());
                return criterionPoint;
            }).collect(Collectors.toList());

            evaluate.setCriterionPoints(criterionPoints);
            evaluate.setTotalScore(totalScore.get());
            return evaluateRepository.save(evaluate);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating Evaluate: " + e.getMessage());
        }
    }

    private int validateAndConvertScore(Integer score) {
        if (score == null) {
            throw new RuntimeException("⚠️ Điểm không được null");
        }
        if (score % 1 != 0) {
            throw new RuntimeException("⚠️ Điểm phải là số nguyên");
        }
        if (score < 0) {
            throw new RuntimeException("⚠️ Điểm không được âm");
        }
        return score;
    }

    public EvaluateResponse getEvaluateById(Long evaluateId) {
        Evaluate evaluate = evaluateRepository.findById(evaluateId)
                .orElseThrow(() -> new RuntimeException("Evaluate not found"));
        return mapToEvaluateResponse(evaluate);
    }

    public List<EvaluateResponse> getEvaluatesByStudent(Long studentId) {
        List<Evaluate> evaluates = evaluateRepository.findByStudent_StudentId(studentId);
        return evaluates.stream()
                .map(this::mapToEvaluateResponse)
                .collect(Collectors.toList());
    }

    private EvaluateResponse mapToEvaluateResponse(Evaluate evaluate) {
        try {
            List<EvaluateResponse.CriterionPointDto> criterionDtos = evaluate.getCriterionPoints().stream()
                    .filter(cp -> cp.getCriterion() != null)
                    .map(cp -> EvaluateResponse.CriterionPointDto.builder()
                            .criterionId(cp.getCriterion().getId())
                            .criterionName(cp.getCriterion().getName())
                            .score(cp.getScore())
                            .detailPoints(cp.getDetailPoints().stream()
                                    .filter(dp -> dp.getDetail() != null)
                                    .map(dp -> EvaluateResponse.CriterionPointDto.DetailPointDto.builder()
                                            .detailId(dp.getDetail().getId())
                                            .detailName(dp.getDetail().getName())
                                            .score(dp.getScore())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build())
                    .collect(Collectors.toList());

            return EvaluateResponse.builder()
                    .evaluateId(evaluate.getId())
                    .studentId(evaluate.getStudent() != null ? evaluate.getStudent().getStudentId() : null)
                    .semesterId(evaluate.getSemester() != null ? evaluate.getSemester().getSemesterId() : null)
                    .totalScore(evaluate.getTotalScore())
                    .criterionPoints(criterionDtos)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("🔥 Lỗi khi mapping EvaluateResponse: " + e.getMessage());
        }
    }

    public List<Evaluate> getAllEvaluates() {
        return evaluateRepository.findAll();
    }

    public Evaluate recalculateEvaluate(Evaluate evaluate) {
        AtomicInteger totalScore = new AtomicInteger(0);

        for (CriterionPoint cp : evaluate.getCriterionPoints()) {
            cp.setEvaluate(evaluate);

            for (DetailPoint dp : cp.getDetailPoints()) {
                dp.setCriterionPoint(cp);
            }

            totalScore.addAndGet(cp.getScore());
        }

        evaluate.setTotalScore(totalScore.get());
        return evaluateRepository.save(evaluate);
    }

    public Evaluate updateEvaluate(Long id, Evaluate updated) {
        Evaluate existing = evaluateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("❌ Evaluate not found with id = " + id));

        existing.setCriterionPoints(updated.getCriterionPoints());
        return recalculateEvaluate(existing);
    }

    public void deleteEvaluate(Long id) {
        Evaluate evaluate = evaluateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluate not found"));
        evaluateRepository.delete(evaluate);
    }

    public Evaluate findById(Long id) {
        return evaluateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Evaluate với id: " + id));
    }
}

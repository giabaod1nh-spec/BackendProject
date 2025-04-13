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
            System.out.println("🟢 Start creating Evaluate");

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

                CriterionPoint criterionPoint = new CriterionPoint();
                System.out.println("🎯 Check evaluate: " + evaluate);
                System.out.println("🎯 Check student: " + student);
                System.out.println("🎯 Check semester: " + semester);
                criterionPoint.setEvaluate(evaluate);
                criterionPoint.setCriterion(criterion);
                criterionPoint.setScore((int) cpReq.getScore());

                List<DetailPoint> detailPoints = cpReq.getDetailPoints().stream().map(dpReq -> {
                    Detail detail = detailRepository.findById(dpReq.getDetailId())
                            .orElseThrow(() -> new RuntimeException("❌ Detail not found"));

                    DetailPoint detailPoint = new DetailPoint();
                    detailPoint.setCriterionPoint(criterionPoint);
                    detailPoint.setDetail(detail);
                    detailPoint.setScore((int) dpReq.getScore());

                    return detailPoint;
                }).collect(Collectors.toList());

                criterionPoint.setDetailPoints(detailPoints);
                totalScore.addAndGet(criterionPoint.getScore());
                return criterionPoint;
            }).collect(Collectors.toList());

            evaluate.setCriterionPoints(criterionPoints);
            evaluate.setTotalScore(totalScore.get());

            System.out.println("✅ Evaluate created successfully");
            return evaluateRepository.save(evaluate);
        } catch (Exception e) {
            System.err.println("🔥 Error while creating Evaluate:");
            e.printStackTrace();
            throw new RuntimeException("Error while creating Evaluate: " + e.getMessage());
        }
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
        List<EvaluateResponse.CriterionPointDto> criterionDtos = evaluate.getCriterionPoints().stream()
                .map(cp -> EvaluateResponse.CriterionPointDto.builder()
                        .criterionId(cp.getCriterion().getId())
                        .criterionName(cp.getCriterion().getName())
                        .score(cp.getScore())
                        .detailPoints(cp.getDetailPoints().stream()
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
                .studentId(evaluate.getStudent().getStudentId())
                .semesterId(evaluate.getSemester().getSemesterId())
                .totalScore(evaluate.getTotalScore())
                .criterionPoints(criterionDtos)
                .build();
    }
}

package com.example.trainning.point.service;

import com.example.trainning.point.entity.*;
import com.example.trainning.point.repository.EvaluateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluateService {

    @Autowired
    private EvaluateRepository evaluateRepository;

    public Evaluate createEvaluate(Evaluate evaluate) {
        int totalScore = 0;

        for (CriterionPoint cp : evaluate.getCriterionPoints()) {
            int criterionScore = 0;

            for (DetailPoint dp : cp.getDetailPoints()) {
                criterionScore += dp.getScore();
                dp.setCriterionPoint(cp); // set lại quan hệ
            }

            cp.setScore(criterionScore);
            cp.setEvaluate(evaluate); // set lại quan hệ
            totalScore += criterionScore;
        }

        evaluate.setTotalScore(totalScore);
        return evaluateRepository.save(evaluate);
    }
}

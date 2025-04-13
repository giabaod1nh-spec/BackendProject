package com.example.trainning.point.dto.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class EvaluateRequest {
    private Long studentId;
    private Long semesterId;
    private List<CriterionPointRequest> criterionPoints;

    @Data
    public static class CriterionPointRequest {
        private Long criterionId;
        private double score;
        private List<DetailPointRequest> detailPoints;

        @Data
        public static class DetailPointRequest {
            private Long detailId;
            private double score;
        }
    }
}


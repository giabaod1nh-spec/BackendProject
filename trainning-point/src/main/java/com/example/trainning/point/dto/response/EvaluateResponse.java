package com.example.trainning.point.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EvaluateResponse {
    private Long evaluateId;
    private Long studentId;
    private Long semesterId;
    private Integer totalScore;
    private List<CriterionPointDto> criterionPoints;

    @Data
    @Builder
    public static class CriterionPointDto {
        private Long criterionId;
        private String criterionName;
        private Integer score;
        private List<DetailPointDto> detailPoints;

        @Data
        @Builder
        public static class DetailPointDto {
            private Long detailId;
            private String detailName;
            private Integer score;
        }
    }
}

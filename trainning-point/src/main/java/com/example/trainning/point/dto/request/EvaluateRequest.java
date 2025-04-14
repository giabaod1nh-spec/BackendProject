package com.example.trainning.point.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class EvaluateRequest {
    @NotNull(message = "studentId không được để trống")
    private Long studentId;

    @NotNull(message = "semesterId không được để trống")
    private Long semesterId;

    @NotEmpty(message = "Danh sách tiêu chí không được để trống")
    @Valid
    private List<CriterionPointRequest> criterionPoints;

    @Data
    public static class CriterionPointRequest {
        @NotNull(message = "criterionId không được để trống")
        private Long criterionId;

        @NotNull(message = "Điểm không được để trống")
        private Integer score;

        @NotEmpty(message = "Danh sách chi tiết điểm không được để trống")
        @Valid
        private List<DetailPointRequest> detailPoints;
    }

    @Data
    public static class DetailPointRequest {
        @NotNull(message = "detailId không được để trống")
        private Long detailId;

        @NotNull(message = "Điểm không được để trống")
        private Integer score;
    }
}


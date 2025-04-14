package com.example.trainning.point.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CriterionResponse {
    Long criterionId;
    String description ;
    Integer maxPoint;
}

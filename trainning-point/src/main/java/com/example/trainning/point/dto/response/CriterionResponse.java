package com.example.trainning.point.dto.response;

import com.example.trainning.point.entity.CriterionPoint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CriterionResponse {
    Long criterionId;
    String description ;
    Integer maxPoint;
    Set<CriterionPoint>  criterionPoints;
}

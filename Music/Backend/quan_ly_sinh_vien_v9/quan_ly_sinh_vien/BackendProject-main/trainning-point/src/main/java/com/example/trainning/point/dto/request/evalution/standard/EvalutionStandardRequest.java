package com.example.trainning.point.dto.request.evalution.standard;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvalutionStandardRequest {
    String name;
    Double minPoint;
    Double maxPoint;
    Long evalutionCategoryId;
}

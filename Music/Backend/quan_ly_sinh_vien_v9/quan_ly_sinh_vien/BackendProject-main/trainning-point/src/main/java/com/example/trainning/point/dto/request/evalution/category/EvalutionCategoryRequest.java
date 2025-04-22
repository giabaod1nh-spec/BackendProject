package com.example.trainning.point.dto.request.evalution.category;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvalutionCategoryRequest {
    String name;
    Double maxPoint;
}

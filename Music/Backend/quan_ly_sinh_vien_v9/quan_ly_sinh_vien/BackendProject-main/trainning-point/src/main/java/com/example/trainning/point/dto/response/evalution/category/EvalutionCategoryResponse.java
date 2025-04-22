package com.example.trainning.point.dto.response.evalution.category;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvalutionCategoryResponse {
    Long id;
    String name;
    Double maxPoint;
}

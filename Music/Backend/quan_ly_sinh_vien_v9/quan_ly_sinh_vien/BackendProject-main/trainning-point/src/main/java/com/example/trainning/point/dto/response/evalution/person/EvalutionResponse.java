package com.example.trainning.point.dto.response.evalution.person;

import com.example.trainning.point.dto.response.evalution.standard.EvalutionStandardResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvalutionResponse {
    Long idCategory;
    String nameCategory;
    Double maxPoint;

    List<EvalutionStandardResponse> standards;
}

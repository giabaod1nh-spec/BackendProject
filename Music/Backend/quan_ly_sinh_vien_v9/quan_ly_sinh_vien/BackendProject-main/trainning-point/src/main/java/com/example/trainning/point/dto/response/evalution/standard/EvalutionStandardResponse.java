package com.example.trainning.point.dto.response.evalution.standard;

import com.example.trainning.point.dto.response.evalution.person.EvalutionPersonResponse;
import com.example.trainning.point.entity.EvalutionPerson;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvalutionStandardResponse {
    Long id;
    String name;
    Double minPoint;
    Double maxPoint;
    Long evalutionCategoryId;
    String evalutionCategoryName;
    private EvalutionPersonResponse evalutionPerson;
}

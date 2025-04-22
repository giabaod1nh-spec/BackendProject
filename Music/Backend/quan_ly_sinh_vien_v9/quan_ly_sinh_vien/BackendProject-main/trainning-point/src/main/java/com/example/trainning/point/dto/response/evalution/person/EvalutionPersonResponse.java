package com.example.trainning.point.dto.response.evalution.person;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvalutionPersonResponse {
    Long id;
    Double studentScore;
    Double monitorScore;
    Double teacherScore;
//    String evalutionStatus;
    String userId;
    Long evalutionStandardId;
}

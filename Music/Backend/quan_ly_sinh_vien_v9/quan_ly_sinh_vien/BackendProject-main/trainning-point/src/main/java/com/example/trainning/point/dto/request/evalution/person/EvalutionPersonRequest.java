package com.example.trainning.point.dto.request.evalution.person;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvalutionPersonRequest {
    Double studentScore;
    Double monitorScore;
    Double teacherScore;
//    Long standardId;
//    String evalutionStatus;
    String userId;
    Long evalutionStandardId;
    Long semesterId;
}

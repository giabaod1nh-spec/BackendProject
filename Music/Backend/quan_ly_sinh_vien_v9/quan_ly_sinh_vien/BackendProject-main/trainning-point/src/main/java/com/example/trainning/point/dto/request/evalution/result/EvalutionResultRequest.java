package com.example.trainning.point.dto.request.evalution.result;

import com.example.trainning.point.dto.response.semester.SemesterResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvalutionResultRequest {
    String status;
    Long semesterId;
}

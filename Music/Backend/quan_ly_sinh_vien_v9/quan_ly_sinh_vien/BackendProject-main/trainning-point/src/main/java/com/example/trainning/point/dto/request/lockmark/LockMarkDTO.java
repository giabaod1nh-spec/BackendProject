package com.example.trainning.point.dto.request.lockmark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LockMarkDTO {
    Long semesterId;
    String userId;
}

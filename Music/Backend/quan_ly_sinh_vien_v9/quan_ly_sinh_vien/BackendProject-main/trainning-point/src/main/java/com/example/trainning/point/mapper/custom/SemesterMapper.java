package com.example.trainning.point.mapper.custom;

import com.example.trainning.point.dto.request.semester.SemesterRequest;
import com.example.trainning.point.dto.response.semester.SemesterResponse;
import com.example.trainning.point.entity.Semester;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SemesterMapper {
    public Semester convertToEntity(SemesterRequest request){
        return Semester.builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    public SemesterResponse convertToResponse(Semester entity){
        return SemesterResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())

                .build();
    }
}

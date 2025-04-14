package com.example.trainning.point.mapper;

import com.example.trainning.point.dto.request.CriterionPointCreationRequest;
import com.example.trainning.point.dto.request.CriterionPointUpdateRequest;
import com.example.trainning.point.dto.request.InstructorUpdateRequest;
import com.example.trainning.point.dto.response.CriterionPointResponse;
import com.example.trainning.point.entity.CriterionPoint;
import com.example.trainning.point.entity.Instructor;
import com.example.trainning.point.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CriterionPointMapper {
    CriterionPoint toCriterionPoint(CriterionPointCreationRequest request);
    CriterionPointResponse toCriterionPointResponse(CriterionPoint criterionPoint);

    void updateCriterionPoint(@MappingTarget CriterionPoint criterionPoint , CriterionPointUpdateRequest request);
}

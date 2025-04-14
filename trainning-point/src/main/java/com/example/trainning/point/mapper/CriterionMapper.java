package com.example.trainning.point.mapper;

import com.example.trainning.point.dto.request.CriterionCreationRequest;
import com.example.trainning.point.dto.request.CriterionUpdateRequest;
import com.example.trainning.point.dto.request.InstructorCreationRequest;
import com.example.trainning.point.dto.request.InstructorUpdateRequest;
import com.example.trainning.point.dto.response.CriterionResponse;
import com.example.trainning.point.dto.response.InstructorResponse;
import com.example.trainning.point.entity.Criterion;
import com.example.trainning.point.entity.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CriterionMapper {
  //  @Mapping(target = "criterionPoints" , ignore = true)
    Criterion toCriterion(CriterionCreationRequest request);

    CriterionResponse toCriterionResponse(Criterion criterion);

    @Mapping(target = "criterionPoints" , ignore = true)
    void updateCriterion(@MappingTarget Criterion criterion , CriterionUpdateRequest request);
}

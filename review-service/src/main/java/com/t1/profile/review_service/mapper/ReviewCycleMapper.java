package com.t1.profile.review_service.mapper;

import com.t1.profile.review_service.dto.ReviewCycleDto;
import com.t1.profile.review_service.model.ReviewCycle;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewCycleMapper extends BaseMapper<ReviewCycleDto, ReviewCycle> {

    @Override
    ReviewCycle toEntity(ReviewCycleDto dto);

    @Override
    ReviewCycleDto toDto(ReviewCycle entity);

    @Override
    List<ReviewCycleDto> toDtoList(List<ReviewCycle> entities);

    @Override
    List<ReviewCycle> toEntityList(List<ReviewCycleDto> dtos);

}

package com.t1.profile.review_service.mapper;

import com.t1.profile.review_service.dto.ReviewTaskDto;
import com.t1.profile.review_service.model.ReviewTask;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewTaskMapper extends BaseMapper<ReviewTaskDto, ReviewTask> {

    @Override
    ReviewTask toEntity(ReviewTaskDto dto);

    @Override
    ReviewTaskDto toDto(ReviewTask entity);

    @Override
    List<ReviewTaskDto> toDtoList(List<ReviewTask> entities);

    @Override
    List<ReviewTask> toEntityList(List<ReviewTaskDto> dtos);

}

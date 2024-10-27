package com.t1.profile.mapper;

import com.t1.profile.dto.SoftSkillRatingDto;
import com.t1.profile.model.SoftSkillRating;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SoftSkillRatingMapper extends BaseMapper<SoftSkillRatingDto, SoftSkillRating> {

    @Override
    SoftSkillRating toEntity(SoftSkillRatingDto dto);

    @Override
    SoftSkillRatingDto toDto(SoftSkillRating entity);

    @Override
    List<SoftSkillRatingDto> toDtoList(List<SoftSkillRating> entities);

    @Override
    List<SoftSkillRating> toEntityList(List<SoftSkillRatingDto> dtos);
}

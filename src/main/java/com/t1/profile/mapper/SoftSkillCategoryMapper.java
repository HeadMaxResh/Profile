package com.t1.profile.mapper;

import com.t1.profile.dto.SoftSkillCategoryDto;
import com.t1.profile.model.SoftSkillCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SoftSkillCategoryMapper extends BaseMapper<SoftSkillCategoryDto, SoftSkillCategory> {

    @Override
    SoftSkillCategory toEntity(SoftSkillCategoryDto dto);

    @Override
    SoftSkillCategoryDto toDto(SoftSkillCategory entity);

    @Override
    List<SoftSkillCategoryDto> toDtoList(List<SoftSkillCategory> entities);

    @Override
    List<SoftSkillCategory> toEntityList(List<SoftSkillCategoryDto> dtos);

}

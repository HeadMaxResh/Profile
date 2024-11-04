package com.t1.profile.skill.soft.mapper;

import com.t1.profile.common.web.mapper.BaseMapper;
import com.t1.profile.skill.soft.dto.SoftSkillCategoryDto;
import com.t1.profile.skill.soft.model.SoftSkillCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
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

package com.t1.profile.skill.soft.mapper;

import com.t1.profile.common.web.mapper.BaseMapper;
import com.t1.profile.skill.soft.dto.SoftSkillDto;
import com.t1.profile.skill.soft.model.SoftSkill;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SoftSkillMapper extends BaseMapper<SoftSkillDto, SoftSkill> {

    @Override
    SoftSkill toEntity(SoftSkillDto dto);

    @Override
    SoftSkillDto toDto(SoftSkill entity);

    @Override
    List<SoftSkillDto> toDtoList(List<SoftSkill> entities);

    @Override
    List<SoftSkill> toEntityList(List<SoftSkillDto> dtos);
}

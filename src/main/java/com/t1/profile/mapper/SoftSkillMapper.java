package com.t1.profile.mapper;

import com.t1.profile.dto.SoftSkillDto;
import com.t1.profile.model.SoftSkill;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
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

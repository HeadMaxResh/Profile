package com.t1.profile.mapper;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.model.HardSkill;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HardSkillMapper extends BaseMapper<HardSkillDto, HardSkill> {

    @Override
    HardSkill toEntity(HardSkillDto dto);

    @Override
    HardSkillDto toDto(HardSkill entity);

    @Override
    List<HardSkillDto> toDtoList(List<HardSkill> entities);

    @Override
    List<HardSkill> toEntityList(List<HardSkillDto> dtos);
}

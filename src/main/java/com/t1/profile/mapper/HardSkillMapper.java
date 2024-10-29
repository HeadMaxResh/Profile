package com.t1.profile.mapper;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.model.HardSkill;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
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

package com.t1.profile.mapper;

import com.t1.profile.dto.UserSoftSkillDto;
import com.t1.profile.model.UserSoftSkill;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserSoftSkillMapper extends BaseMapper<UserSoftSkillDto, UserSoftSkill> {

    @Override
    UserSoftSkill toEntity(UserSoftSkillDto dto);

    @Override
    UserSoftSkillDto toDto(UserSoftSkill entity);

    @Override
    List<UserSoftSkillDto> toDtoList(List<UserSoftSkill> entities);

    @Override
    List<UserSoftSkill> toEntityList(List<UserSoftSkillDto> dtos);
}

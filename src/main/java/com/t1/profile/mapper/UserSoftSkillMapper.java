package com.t1.profile.mapper;

import com.t1.profile.dto.UserSoftSkillResponseDto;
import com.t1.profile.model.UserSoftSkill;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserSoftSkillMapper extends BaseMapper<UserSoftSkillResponseDto, UserSoftSkill> {

    @Override
    UserSoftSkill toEntity(UserSoftSkillResponseDto dto);

    @Override
    UserSoftSkillResponseDto toDto(UserSoftSkill entity);

    @Override
    List<UserSoftSkillResponseDto> toDtoList(List<UserSoftSkill> entities);

    @Override
    List<UserSoftSkill> toEntityList(List<UserSoftSkillResponseDto> dtos);
}

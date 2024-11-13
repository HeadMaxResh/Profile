package com.t1.profile.user.mapper;

import com.t1.profile.common.web.mapper.BaseMapper;
import com.t1.profile.user.dto.UserDetailsDto;
import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserDetailsMapper extends BaseMapper<UserDetailsDto, User> {


    @Override
    User toEntity(UserDetailsDto dto);

    @Override
    UserDetailsDto toDto(User entity);

    @Override
    List<UserDetailsDto> toDtoList(List<User> entities);

    @Override
    List<User> toEntityList(List<UserDetailsDto> dtos);

}
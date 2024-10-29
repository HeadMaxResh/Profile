package com.t1.profile.mapper;

import com.t1.profile.dto.UserDto;
import com.t1.profile.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseMapper<UserDto, User>{

    @Override
    User toEntity(UserDto dto);

    @Override
    UserDto toDto(User entity);

    @Override
    List<UserDto> toDtoList(List<User> entities);

    @Override
    List<User> toEntityList(List<UserDto> dtos);

}

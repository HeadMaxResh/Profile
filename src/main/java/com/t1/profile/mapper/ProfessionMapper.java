package com.t1.profile.mapper;

import com.t1.profile.dto.ProfessionDto;
import com.t1.profile.model.Profession;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProfessionMapper extends BaseMapper<ProfessionDto, Profession>  {

    @Override
    Profession toEntity(ProfessionDto dto);

    @Override
    ProfessionDto toDto(Profession entity);

    @Override
    List<ProfessionDto> toDtoList(List<Profession> entities);

    @Override
    List<Profession> toEntityList(List<ProfessionDto> dtos);

}

package com.t1.profile.mapper;

import com.t1.profile.dto.ProfessionDto;
import com.t1.profile.model.Profession;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
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

package com.t1.profile.profession.mapper;

import com.t1.profile.common.web.mapper.BaseMapper;
import com.t1.profile.profession.dto.ProfessionDto;
import com.t1.profile.profession.model.Profession;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfessionMapper extends BaseMapper<ProfessionDto, Profession> {

    @Override
    Profession toEntity(ProfessionDto dto);

    @Override
    ProfessionDto toDto(Profession entity);

    @Override
    List<ProfessionDto> toDtoList(List<Profession> entities);

    @Override
    List<Profession> toEntityList(List<ProfessionDto> dtos);

}

package com.t1.profile.dto;

import lombok.Data;

import java.util.List;

@Data
public class SoftSkillCategoryWithSkillsDto {

    private Integer id;
    private String name;
    private List<SoftSkillDto> softSkills;

}

package com.t1.profile.skill.soft.dto;

import lombok.Data;

import java.util.List;

@Data
public class SoftSkillCategoryWithSkillsDto {

    private Integer id;
    private String name;
    private List<SoftSkillDto> softSkills;

}

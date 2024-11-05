package com.t1.profile.skill.hard.dto;

import lombok.Data;

@Data
public class UserHardSkillDto {

    private Long id;
    private HardSkillDto hardSkill;
    private Integer rating;

}

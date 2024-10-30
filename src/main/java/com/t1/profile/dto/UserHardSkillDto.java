package com.t1.profile.dto;

import lombok.Data;

@Data
public class UserHardSkillDto {

    private Integer id;
    private HardSkillDto hardSkill;
    private Integer rating;

}

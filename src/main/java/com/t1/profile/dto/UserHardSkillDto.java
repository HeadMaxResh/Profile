package com.t1.profile.dto;

import com.t1.profile.model.HardSkill;
import lombok.Data;

@Data
public class UserHardSkillDto {

    private Integer id;
    private HardSkill hardSkill;
    private Integer rating;

}

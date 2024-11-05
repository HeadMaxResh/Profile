package com.t1.profile.skill.soft.dto;

import lombok.Data;

@Data
public class UserSoftSkillRequestDto {

    private Integer softSkillId;
    private Long ratedUserId;
    private Long raterUserId;
    private Integer rating;

}

package com.t1.profile.skill.soft.dto;

import lombok.Data;

@Data
public class UserSoftSkillRequestDto {

    private Integer softSkillId;
    private Integer ratedUserId;
    private Integer raterUserId;
    private Integer rating;

}

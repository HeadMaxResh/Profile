package com.t1.profile.dto;

import lombok.Data;

@Data
public class UserSoftSkillResponseDto {

    private SoftSkillDto softSkill;
    private UserSummaryDto ratedUser;
    private UserSummaryDto raterUser;
    private Integer rating;

}

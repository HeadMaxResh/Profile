package com.t1.profile.skill.soft.dto;

import com.t1.profile.user.dto.UserSummaryDto;
import lombok.Data;

@Data
public class UserSoftSkillResponseDto {

    private Integer id;
    private SoftSkillDto softSkill;
    private UserSummaryDto ratedUser;
    private UserSummaryDto raterUser;
    private Integer rating;

}

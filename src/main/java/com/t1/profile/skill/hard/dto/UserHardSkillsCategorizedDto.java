package com.t1.profile.skill.hard.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserHardSkillsCategorizedDto {

    public UserHardSkillsCategorizedDto(List<UserHardSkillDto> commonHardSkills, List<UserHardSkillDto> remainingUserHardSkills) {
        this.commonHardSkills = commonHardSkills;
        this.remainingUserHardSkills = remainingUserHardSkills;
    }

    private List<UserHardSkillDto> commonHardSkills;
    private List<UserHardSkillDto> remainingUserHardSkills;

}

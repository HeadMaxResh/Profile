package com.t1.profile.dto;

import com.t1.profile.model.UserHardSkill;
import lombok.Data;

import java.util.List;

@Data
public class UserHardSkillsCategorizedDto {

    public UserHardSkillsCategorizedDto(List<UserHardSkill> commonHardSkills, List<UserHardSkill> remainingUserHardSkills) {
        this.commonHardSkills = commonHardSkills;
        this.remainingUserHardSkills = remainingUserHardSkills;
    }

    private List<UserHardSkill> commonHardSkills;
    private List<UserHardSkill> remainingUserHardSkills;

}

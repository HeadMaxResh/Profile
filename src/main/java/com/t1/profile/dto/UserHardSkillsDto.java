package com.t1.profile.dto;

import com.t1.profile.model.HardSkill;
import lombok.Data;

import java.util.List;

@Data
public class UserHardSkillsDto {

    public UserHardSkillsDto(List<HardSkill> commonHardSkills, List<HardSkill> remainingUserHardSkills) {
        this.commonHardSkills = commonHardSkills;
        this.remainingUserHardSkills = remainingUserHardSkills;
    }

    private List<HardSkill> commonHardSkills;
    private List<HardSkill> remainingUserHardSkills;

}

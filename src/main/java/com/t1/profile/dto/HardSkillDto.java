package com.t1.profile.dto;

import com.t1.profile.enums.HardSkillType;
import lombok.Data;

@Data
public class HardSkillDto {

    private String name;
    private HardSkillType type;

}

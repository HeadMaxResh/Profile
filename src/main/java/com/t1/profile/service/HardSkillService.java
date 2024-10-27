package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;

public interface HardSkillService {

    HardSkillDto addHardSkill(HardSkillDto hardSkillDto);
    HardSkillDto updateHardSkill(Integer hardSkillId, HardSkillDto hardSkillDto);
    void deleteHardSkill(Integer hardSkillId);

}

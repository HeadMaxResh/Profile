package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;

import java.util.List;

public interface HardSkillService {

    List<HardSkillDto> getAllHardSkills();
    HardSkillDto addHardSkill(HardSkillDto hardSkillDto);
    HardSkillDto updateHardSkill(Integer hardSkillId, HardSkillDto hardSkillDto);
    void deleteHardSkill(Integer hardSkillId);

}

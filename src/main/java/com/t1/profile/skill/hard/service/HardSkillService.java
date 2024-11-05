package com.t1.profile.skill.hard.service;

import com.t1.profile.skill.hard.dto.HardSkillDto;

import java.util.List;

public interface HardSkillService {

    List<HardSkillDto> getAllHardSkills();
    HardSkillDto addHardSkill(HardSkillDto hardSkillDto);
    HardSkillDto updateHardSkill(Long hardSkillId, HardSkillDto hardSkillDto);
    void deleteHardSkill(Long hardSkillId);

}

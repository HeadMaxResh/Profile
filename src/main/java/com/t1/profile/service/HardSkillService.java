package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.model.HardSkill;

import java.util.List;

public interface HardSkillService {


    HardSkill addHardSkill(HardSkillDto hardSkillDto);
    HardSkill updateHardSkill(Integer hardSkillId, HardSkillDto hardSkillDto);
    void deleteHardSkill(Integer hardSkillId);
    List<HardSkillDto> getAllHardSkills();


}

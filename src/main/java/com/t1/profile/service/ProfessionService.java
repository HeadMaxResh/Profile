package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.dto.ProfessionDto;

import java.util.Set;

public interface ProfessionService {

    ProfessionDto addProfession(ProfessionDto professionDto);
    HardSkillDto addHardSkillToProfession(Integer professionId, HardSkillDto hardSkillDto);
    HardSkillDto addExistingHardSkillToProfession(Integer professionId, Integer hardSkillId);
    void removeHardSkillFromProfession(Integer professionId, Integer hardSkillId);
    void deleteProfession(Integer professionId);
    Set<HardSkillDto> getHardSkillsByProfession(Integer professionId);

}

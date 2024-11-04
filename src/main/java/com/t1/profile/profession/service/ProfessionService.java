package com.t1.profile.profession.service;

import com.t1.profile.skill.hard.dto.HardSkillDto;
import com.t1.profile.profession.dto.ProfessionDto;

import java.util.List;
import java.util.Set;

public interface ProfessionService {

    List<ProfessionDto> getAllProfessions();
    ProfessionDto addProfession(ProfessionDto professionDto);
    HardSkillDto addHardSkillToProfession(Integer professionId, HardSkillDto hardSkillDto);
    HardSkillDto addExistingHardSkillToProfession(Integer professionId, Integer hardSkillId);
    void removeHardSkillFromProfession(Integer professionId, Integer hardSkillId);
    void deleteProfession(Integer professionId);
    Set<HardSkillDto> getHardSkillsByProfession(Integer professionId);

}

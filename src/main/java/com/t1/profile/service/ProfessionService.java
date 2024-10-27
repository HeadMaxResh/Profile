package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.dto.ProfessionDto;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;

import java.util.List;
import java.util.Set;

public interface ProfessionService {

    Profession addProfession(ProfessionDto professionDto);
    HardSkill addHardSkillToProfession(Integer professionId, HardSkillDto hardSkillDto);
    HardSkill addExistingHardSkillToProfession(Integer professionId, Integer hardSkillId);
    void removeHardSkillFromProfession(Integer professionId, Integer hardSkillId);
    void deleteProfession(Integer professionId);
    Set<HardSkill> getHardSkillsByProfession(Integer professionId);
    List<Profession> getAllProfessions();
}

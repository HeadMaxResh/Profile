package com.t1.profile.profession.service;

import com.t1.profile.skill.hard.dto.HardSkillDto;
import com.t1.profile.profession.dto.ProfessionDto;

import java.util.List;
import java.util.Set;

public interface ProfessionService {

    List<ProfessionDto> getAllProfessions();
    ProfessionDto addProfession(ProfessionDto professionDto);
    HardSkillDto addHardSkillToProfession(Long professionId, HardSkillDto hardSkillDto);
    HardSkillDto addExistingHardSkillToProfession(Long professionId, Long hardSkillId);
    void removeHardSkillFromProfession(Long professionId, Long hardSkillId);
    void deleteProfession(Long professionId);
    Set<HardSkillDto> getHardSkillsByProfession(Long professionId);

}

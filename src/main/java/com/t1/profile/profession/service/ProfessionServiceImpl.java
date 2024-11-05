package com.t1.profile.profession.service;

import com.t1.profile.profession.exception.ProfessionNotFoundException;
import com.t1.profile.skill.hard.dto.HardSkillDto;
import com.t1.profile.profession.dto.ProfessionDto;
import com.t1.profile.skill.hard.exception.HardSkillNotFoundException;
import com.t1.profile.skill.hard.mapper.HardSkillMapper;
import com.t1.profile.profession.mapper.ProfessionMapper;
import com.t1.profile.skill.hard.model.HardSkill;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.skill.hard.repository.HardSkillRepo;
import com.t1.profile.profession.repository.ProfessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    @Autowired
    private ProfessionRepo professionRepo;

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Autowired
    private ProfessionMapper professionMapper;

    @Autowired
    private HardSkillMapper hardSkillMapper;

    @Override
    public List<ProfessionDto> getAllProfessions() {
        List<Profession> professions = professionRepo.findAll()
                .stream()
                .collect(Collectors.toList()); // Получите все профессии
        return professions.stream()
                .map(professionMapper::toDto) // Преобразуйте их в ProfessionDto
                .collect(Collectors.toList());
    }

    @Override
    public ProfessionDto addProfession(ProfessionDto professionDto) {
        Profession profession = new Profession();
        profession.setName(professionDto.getName());
        Profession savedProfession = professionRepo.save(profession);
        return professionMapper.toDto(savedProfession);
    }

    @Override
    public HardSkillDto addHardSkillToProfession(Long professionId, HardSkillDto hardSkillDto) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());
        //hardSkill.setType(hardSkillDto.getType());

        hardSkill.setProfession(profession);
        profession.getMainHardSkills().add(hardSkill);
        HardSkill savedHardSkill = hardSkillRepo.save(hardSkill);
        return hardSkillMapper.toDto(savedHardSkill);
    }

    @Override
    public HardSkillDto  addExistingHardSkillToProfession(Long professionId, Long hardSkillId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new HardSkillNotFoundException("HardSkill not found with id " + hardSkillId));

        hardSkill.setProfession(profession);
        profession.getMainHardSkills().add(hardSkill);
        professionRepo.save(profession);
        return hardSkillMapper.toDto(hardSkill);
    }

    @Override
    public void removeHardSkillFromProfession(Long professionId, Long hardSkillId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new HardSkillNotFoundException("HardSkill not found with id " + hardSkillId));

        hardSkill.setProfession(null);
        profession.getMainHardSkills().remove(hardSkill);
        professionRepo.save(profession);
    }

    @Override
    public void deleteProfession(Long professionId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException("Profession not found with id " + professionId));

        professionRepo.delete(profession);
    }

    @Override
    public Set<HardSkillDto> getHardSkillsByProfession(Long professionId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException("Profession not found with id " + professionId));

        return profession.getMainHardSkills().stream()
                .map(hardSkillMapper::toDto)
                .collect(Collectors.toSet());
    }

}

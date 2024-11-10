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
        return professionRepo.findAll()
                .stream()
                .map(professionMapper::toDto)
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
    public HardSkillDto addHardSkillToProfession(Integer professionId, HardSkillDto hardSkillDto) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException(professionId));

        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());

        hardSkill.setProfession(profession);
        profession.getMainHardSkills().add(hardSkill);
        HardSkill savedHardSkill = hardSkillRepo.save(hardSkill);
        return hardSkillMapper.toDto(savedHardSkill);
    }

    @Override
    public HardSkillDto  addExistingHardSkillToProfession(Integer professionId, Integer hardSkillId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException(professionId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new HardSkillNotFoundException(hardSkillId));

        hardSkill.setProfession(profession);
        profession.getMainHardSkills().add(hardSkill);
        professionRepo.save(profession);
        return hardSkillMapper.toDto(hardSkill);
    }

    @Override
    public void removeHardSkillFromProfession(Integer professionId, Integer hardSkillId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException(professionId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new HardSkillNotFoundException(hardSkillId));

        hardSkill.setProfession(null);
        profession.getMainHardSkills().remove(hardSkill);
        professionRepo.save(profession);
    }

    @Override
    public void deleteProfession(Integer professionId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException(professionId));

        professionRepo.delete(profession);
    }

    @Override
    public Set<HardSkillDto> getHardSkillsByProfession(Integer professionId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ProfessionNotFoundException(professionId));

        return profession.getMainHardSkills().stream()
                .map(hardSkillMapper::toDto)
                .collect(Collectors.toSet());
    }

}

package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.dto.ProfessionDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.HardSkillMapper;
import com.t1.profile.mapper.ProfessionMapper;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.repository.HardSkillRepo;
import com.t1.profile.repository.ProfessionRepo;
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
        List<Profession> professions = professionRepo.findAll(); // Получите все профессии
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
    public HardSkillDto addHardSkillToProfession(Integer professionId, HardSkillDto hardSkillDto) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());
        //hardSkill.setType(hardSkillDto.getType());

        profession.getMainHardSkills().add(hardSkill);
        HardSkill savedHardSkill = hardSkillRepo.save(hardSkill);
        return hardSkillMapper.toDto(savedHardSkill);
    }

    @Override
    public HardSkillDto  addExistingHardSkillToProfession(Integer professionId, Integer hardSkillId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        profession.getMainHardSkills().add(hardSkill);
        professionRepo.save(profession);
        return hardSkillMapper.toDto(hardSkill);
    }

    @Override
    public void removeHardSkillFromProfession(Integer professionId, Integer hardSkillId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        profession.getMainHardSkills().remove(hardSkill);
        professionRepo.save(profession);
    }

    @Override
    public void deleteProfession(Integer professionId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        professionRepo.delete(profession);
    }

    @Override
    public Set<HardSkillDto> getHardSkillsByProfession(Integer professionId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        return profession.getMainHardSkills().stream()
                .map(hardSkillMapper::toDto)
                .collect(Collectors.toSet());
    }

}

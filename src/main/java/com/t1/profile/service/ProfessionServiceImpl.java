package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.dto.ProfessionDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.repository.HardSkillRepo;
import com.t1.profile.repository.ProfessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    @Autowired
    private ProfessionRepo professionRepo;

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Override
    public Profession addProfession(ProfessionDto professionDto) {
        Profession profession = new Profession();
        profession.setName(professionDto.getName());
        return professionRepo.save(profession);
    }

    @Override
    public HardSkill addHardSkillToProfession(Integer professionId, HardSkillDto hardSkillDto) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());
        //hardSkill.setType(hardSkillDto.getType());

        profession.getMainHardSkills().add(hardSkill);
        HardSkill savedHardSkill = hardSkillRepo.save(hardSkill);
        return savedHardSkill;
    }

    @Override
    public HardSkill addExistingHardSkillToProfession(Integer professionId, Integer hardSkillId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        profession.getMainHardSkills().add(hardSkill);
        professionRepo.save(profession);
        return hardSkill;
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
    public Set<HardSkill> getHardSkillsByProfession(Integer professionId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        return profession.getMainHardSkills();
    }
    //добавил
    @Override
    public List<Profession> getAllProfessions() {
        return (List<Profession>) professionRepo.findAll();
    }

}

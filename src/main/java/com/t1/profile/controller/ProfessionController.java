package com.t1.profile.controller;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.dto.ProfessionDto;
import com.t1.profile.exception.ResourceNotFoundException;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.repository.HardSkillRepo;
import com.t1.profile.repository.ProfessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professions")
public class ProfessionController {

    @Autowired
    private ProfessionRepo professionRepo;

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @PostMapping("/add")
    public ResponseEntity<Profession> addProfession(@RequestBody ProfessionDto professionDto) {
        Profession profession = new Profession();
        profession.setName(professionDto.getName());

        Profession savedProfession = professionRepo.save(profession);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfession);
    }

    @PostMapping("/{professionId}/add-new-hard-skill")
    public ResponseEntity<HardSkill> addHardSkillToProfession(
            @PathVariable Integer professionId,
            @RequestBody HardSkillDto hardSkillDto
    ) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());
        hardSkill.setType(hardSkillDto.getType());

        profession.getMainHardSkills().add(hardSkill);

        hardSkillRepo.save(hardSkill);
        professionRepo.save(profession);

        return ResponseEntity.status(HttpStatus.CREATED).body(hardSkill);
    }

    @PostMapping("/{professionId}/add-existing-hard-skill/{hardSkillId}")
    public ResponseEntity<HardSkill> addExistingHardSkillToProfession(
            @PathVariable Integer professionId,
            @PathVariable Integer hardSkillId
    ) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        profession.getMainHardSkills().add(hardSkill);

        professionRepo.save(profession);

        return ResponseEntity.status(HttpStatus.OK).body(hardSkill);
    }

    @DeleteMapping("/{professionId}/remove-hard-skills/{hardSkillId}")
    public ResponseEntity<Void> removeHardSkillFromProfession(
            @PathVariable Integer professionId,
            @PathVariable Integer hardSkillId
    ) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        profession.getMainHardSkills().remove(hardSkill);

        professionRepo.save(profession);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{professionId}")
    public ResponseEntity<Void> deleteProfession(@PathVariable Integer professionId) {
        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        professionRepo.delete(profession);

        return ResponseEntity.noContent().build();
    }
}

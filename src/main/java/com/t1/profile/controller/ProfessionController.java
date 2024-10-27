package com.t1.profile.controller;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.dto.ProfessionDto;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/professions")
public class ProfessionController {

    private final ProfessionService professionService;

    @Autowired
    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping
    public ResponseEntity<List<Profession>> getAllProfessions() {
        List<Profession> professions = professionService.getAllProfessions();
        return ResponseEntity.ok(professions);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Profession> addProfession(@RequestBody ProfessionDto professionDto) {
        Profession savedProfession = professionService.addProfession(professionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfession);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{professionId}/add-new-hard-skill")
    public ResponseEntity<HardSkill> addHardSkillToProfession(
            @PathVariable Integer professionId,
            @RequestBody HardSkillDto hardSkillDto
    ) {
        HardSkill savedHardSkill = professionService.addHardSkillToProfession(professionId, hardSkillDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHardSkill);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{professionId}/add-existing-hard-skill/{hardSkillId}")
    public ResponseEntity<HardSkill> addExistingHardSkillToProfession(
            @PathVariable Integer professionId,
            @PathVariable Integer hardSkillId
    ) {
        HardSkill savedHardSkill = professionService.addExistingHardSkillToProfession(professionId, hardSkillId);
        return ResponseEntity.status(HttpStatus.OK).body(savedHardSkill);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{professionId}/remove-hard-skills/{hardSkillId}")
    public ResponseEntity<Void> removeHardSkillFromProfession(
            @PathVariable Integer professionId,
            @PathVariable Integer hardSkillId
    ) {
        professionService.removeHardSkillFromProfession(professionId, hardSkillId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{professionId}/delete")
    public ResponseEntity<Void> deleteProfession(@PathVariable Integer professionId) {
        professionService.deleteProfession(professionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{professionId}/hard-skills")
    public ResponseEntity<Set<HardSkill>> getHardSkillsByProfession(@PathVariable Integer professionId) {
        Set<HardSkill> hardSkills = professionService.getHardSkillsByProfession(professionId);
        return ResponseEntity.ok(hardSkills);
    }
}

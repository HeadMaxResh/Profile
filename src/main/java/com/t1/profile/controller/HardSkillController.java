package com.t1.profile.controller;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.HardSkill;
import com.t1.profile.repository.HardSkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hard-skill")
public class HardSkillController {



    @Autowired
    private HardSkillRepo hardSkillRepo;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<HardSkill> addHardSkill(@RequestBody HardSkillDto hardSkillDto) {
        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());
        hardSkill.setType(hardSkillDto.getType());

        HardSkill savedHardSkill = hardSkillRepo.save(hardSkill);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHardSkill);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{hardSkillId}/update")
    public ResponseEntity<HardSkill> updateHardSkill(
            @PathVariable Integer hardSkillId,
            @RequestBody HardSkillDto hardSkillDto
    ) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        hardSkill.setName(hardSkillDto.getName());
        hardSkill.setType(hardSkillDto.getType());

        HardSkill updatedHardSkill = hardSkillRepo.save(hardSkill);
        return ResponseEntity.ok(updatedHardSkill);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{hardSkillId}/delete")
    public ResponseEntity<Void> deleteHardSkill(@PathVariable Integer hardSkillId) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        hardSkillRepo.delete(hardSkill);

        return ResponseEntity.noContent().build();
    }
}

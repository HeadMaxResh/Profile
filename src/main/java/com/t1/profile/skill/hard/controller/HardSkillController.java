package com.t1.profile.skill.hard.controller;

import com.t1.profile.skill.hard.dto.HardSkillDto;
import com.t1.profile.skill.hard.service.HardSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hard-skills")
public class HardSkillController {

    @Autowired
    private HardSkillService hardSkillService;

    @GetMapping("/all")
    public List<HardSkillDto> getAllHardSkills() {
        return hardSkillService.getAllHardSkills();
    }

    @PostMapping("/add")
    public ResponseEntity<HardSkillDto> addHardSkill(@RequestBody HardSkillDto hardSkillDto) {
        HardSkillDto savedHardSkill = hardSkillService.addHardSkill(hardSkillDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHardSkill);
    }

    @PutMapping("/{hardSkillId}/update")
    public ResponseEntity<HardSkillDto> updateHardSkill(
            @PathVariable Long hardSkillId,
            @RequestBody HardSkillDto hardSkillDto
    ) {
        HardSkillDto updatedHardSkill = hardSkillService.updateHardSkill(hardSkillId, hardSkillDto);
        return ResponseEntity.ok(updatedHardSkill);
    }

    @DeleteMapping("/{hardSkillId}/delete")
    public ResponseEntity<Void> deleteHardSkill(@PathVariable Long hardSkillId) {
        hardSkillService.deleteHardSkill(hardSkillId);
        return ResponseEntity.noContent().build();
    }
}

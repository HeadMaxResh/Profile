package com.t1.profile.controller;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.service.HardSkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hard-skills")
public class HardSkillController {

    @Autowired
    private HardSkillServiceImpl hardSkillService;

    @PostMapping("/add")
    public ResponseEntity<HardSkillDto> addHardSkill(@RequestBody HardSkillDto hardSkillDto) {
        HardSkillDto savedHardSkill = hardSkillService.addHardSkill(hardSkillDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHardSkill);
    }

    @PutMapping("/{hardSkillId}/update")
    public ResponseEntity<HardSkillDto> updateHardSkill(
            @PathVariable Integer hardSkillId,
            @RequestBody HardSkillDto hardSkillDto
    ) {
        HardSkillDto updatedHardSkill = hardSkillService.updateHardSkill(hardSkillId, hardSkillDto);
        return ResponseEntity.ok(updatedHardSkill);
    }

    @DeleteMapping("/{hardSkillId}/delete")
    public ResponseEntity<Void> deleteHardSkill(@PathVariable Integer hardSkillId) {
        hardSkillService.deleteHardSkill(hardSkillId);
        return ResponseEntity.noContent().build();
    }
}

package com.t1.profile.controller;

import com.t1.profile.dto.UserDto;
import com.t1.profile.dto.UserHardSkillsDto;
import com.t1.profile.model.HardSkill;
import com.t1.profile.service.UserHardSkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users/{userId}/hard-skills")
public class UserHardSkillController {

    @Autowired
    private UserHardSkillServiceImpl userHardSkillService;

    @GetMapping("/")
    public ResponseEntity<Set<HardSkill>> getHardSkillsByUser(@PathVariable Integer userId) {
        Set<HardSkill> hardSkills = userHardSkillService.getHardSkillsByUser(userId);
        return ResponseEntity.ok(hardSkills);
    }

    @PostMapping("/{hardSkillId}/add")
    public ResponseEntity<UserDto> addHardSkillToUser(
            @PathVariable Integer userId,
            @PathVariable Integer hardSkillId
    ) {
        UserDto updatedUser = userHardSkillService.addHardSkillToUser(userId, hardSkillId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{hardSkillId}/delete")
    public ResponseEntity<Void> removeHardSkillFromUser(
            @PathVariable Integer userId,
            @PathVariable Integer hardSkillId
    ) {
        userHardSkillService.removeHardSkillFromUser(userId, hardSkillId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user-profession")
    public ResponseEntity<UserHardSkillsDto> getUserAndProfessionHardSkills(
            @PathVariable Integer userId
    ) {
        UserHardSkillsDto response = userHardSkillService.getUserAndProfessionHardSkills(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-profession/{professionId}")
    public ResponseEntity<UserHardSkillsDto> getUserAndProfessionHardSkills(
            @PathVariable Integer userId,
            @PathVariable Integer professionId
    ) {
        UserHardSkillsDto response = userHardSkillService.getUserAndProfessionHardSkills(userId, professionId);
        return ResponseEntity.ok(response);
    }


}

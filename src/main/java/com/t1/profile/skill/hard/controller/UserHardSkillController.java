package com.t1.profile.skill.hard.controller;

import com.t1.profile.user.dto.UserDto;
import com.t1.profile.skill.hard.dto.UserHardSkillDto;
import com.t1.profile.skill.hard.dto.UserHardSkillsCategorizedDto;
import com.t1.profile.skill.hard.service.UserHardSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users/{userId}/hard-skills")
public class UserHardSkillController {

    @Autowired
    private UserHardSkillService userHardSkillService;

    @GetMapping("/")
    public ResponseEntity<Set<UserHardSkillDto>> getHardSkillsByUser(@PathVariable Long userId) {
        Set<UserHardSkillDto> hardSkills = userHardSkillService.getHardSkillsByUser(userId);
        return ResponseEntity.ok(hardSkills);
    }

    @PostMapping("/{hardSkillId}/add/{rating}")
    public ResponseEntity<UserDto> addHardSkillToUser(
            @PathVariable Long userId,
            @PathVariable Long hardSkillId,
            @PathVariable Integer rating
    ) {
        UserDto updatedUser = userHardSkillService.addHardSkillToUser(userId, hardSkillId, rating);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{hardSkillId}/delete")
    public ResponseEntity<Void> removeHardSkillFromUser(
            @PathVariable Long userId,
            @PathVariable Long hardSkillId
    ) {
        userHardSkillService.removeHardSkillFromUser(userId, hardSkillId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user-profession")
    public ResponseEntity<UserHardSkillsCategorizedDto> getUserAndProfessionHardSkills(
            @PathVariable Long userId
    ) {
        UserHardSkillsCategorizedDto response = userHardSkillService.getUserAndProfessionHardSkills(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-profession/{professionId}")
    public ResponseEntity<UserHardSkillsCategorizedDto> getUserAndProfessionHardSkills(
            @PathVariable Long userId,
            @PathVariable Long professionId
    ) {
        UserHardSkillsCategorizedDto response = userHardSkillService.getUserAndProfessionHardSkills(userId, professionId);
        return ResponseEntity.ok(response);
    }


}

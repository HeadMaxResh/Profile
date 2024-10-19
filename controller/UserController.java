package com.t1.profile.controller;

import com.t1.profile.enums.HardSkillType;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.model.User;
import com.t1.profile.repository.HardSkillRepo;
import com.t1.profile.repository.ProfessionRepo;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProfessionRepo professionRepo;
    @Autowired
    private HardSkillRepo hardSkillRepo;

    @GetMapping("/users")
    List<User> getAll() {
        List<User> userList = new ArrayList<>();
        for (User user : userRepo.findAll()) {
            userList.add(user);
        }
        return userList;
    }

    @PostMapping("/users/{userId}/profession/{professionId}/add")
    public ResponseEntity<User> addProfessionToUser(
            @PathVariable Integer userId,
            @PathVariable Integer professionId
    ) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        user.setProfession(profession);

        User updatedUser = userRepo.save(user);

        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/users/{userId}/hard-skill/{hardSkillId}/add")
    public ResponseEntity<User> addHardSkillToUser(
            @PathVariable Integer userId,
            @PathVariable Integer hardSkillId
    ) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        if (user.getProfession() == null || !user.getProfession().getMainHardSkills().contains(hardSkill)) {
            throw new IllegalArgumentException("The hard skill does not belong to the user's profession.");
        }

        if (hardSkill.getType() == HardSkillType.PRIMARY) {
            user.getMainHardSkills().add(hardSkill);
        } else {
            user.getAdditionalHardSkills().add(hardSkill);
        }

        User updatedUser = userRepo.save(user);

        return  ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/users/{userId}/profession/{professionId}/update")
    public ResponseEntity<User> updateProfessionForUser(
            @PathVariable Integer userId,
            @PathVariable Integer professionId
    ) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Profession newProfession = professionRepo.findById(professionId)
                .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + professionId));

        Set<HardSkill> oldMainHardSkills = new HashSet<>(user.getMainHardSkills());
        Set<HardSkill> oldAdditionalHardSkills = new HashSet<>(user.getAdditionalHardSkills());

        user.setProfession(newProfession);

        user.getAdditionalHardSkills().addAll(oldMainHardSkills);
        user.getMainHardSkills().clear();

        for (HardSkill hardSkill : oldAdditionalHardSkills) {
            if (newProfession.getMainHardSkills().contains(hardSkill)) {
                user.getMainHardSkills().add(hardSkill);
            }
        }

        User updatedUser = userRepo.save(user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{userId}/hard-skill/{hardSkillId}/delete")
    public ResponseEntity<Void> removeHardSkillFromUser(
            @PathVariable Integer userId,
            @PathVariable Integer hardSkillId
    ) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        if (user.getMainHardSkills().remove(hardSkill) || user.getAdditionalHardSkills().remove(hardSkill)) {
            userRepo.save(user);
            return ResponseEntity.noContent().build();
        } else {
            throw new IllegalArgumentException("The hard skill is not associated with the user.");
        }
    }
}

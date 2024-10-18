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
import java.util.List;


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
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        userRepo.findAll().forEach(userList::add);
        return userList;
    }

    // Объединённый метод для добавления или обновления профессии пользователя
    @PostMapping("/users/{userId}/profession/{professionId}")
    public ResponseEntity<User> assignOrUpdateProfessionForUser(
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

    @PostMapping("/users/{userId}/hard-skill/{hardSkillId}")
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

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{userId}/delete-hard-skill/{hardSkillId}")
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

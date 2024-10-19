package com.t1.profile.controller;

import com.t1.profile.dto.UserDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.Role;
import com.t1.profile.model.User;
import com.t1.profile.repository.RoleRepo;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // Импортируем PasswordEncoder
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfessionRepo professionRepo;
    @Autowired
    private HardSkillRepo hardSkillRepo;

    // Метод для регистрации
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        if (userRepo.findByEmail(userDto.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Шифруем пароль

        // Назначаем роль по умолчанию
        Role userRole = roleRepo.findByName("ROLE_USER");
        user.setRoles(Collections.singleton(userRole));

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepo.save(user));
    }


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

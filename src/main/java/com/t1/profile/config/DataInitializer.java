package com.t1.profile.config;

import com.t1.profile.model.*;
import com.t1.profile.repository.RoleRepo;
import com.t1.profile.repository.UserRepo;
import com.t1.profile.repository.CategorySoftSkillRepo;
import com.t1.profile.repository.SoftSkillRepo;
import com.t1.profile.repository.SoftSkillIndicatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private CategorySoftSkillRepo categorySoftSkillRepo;

    @Autowired
    private SoftSkillRepo softSkillRepo;

    @Autowired
    private SoftSkillIndicatorRepo softSkillIndicatorRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
        initializeAdminUser();
        initializeSoftSkillsData();
    }

    private void initializeRoles() {
        addRoleIfNotExists("ROLE_USER");
        addRoleIfNotExists("ROLE_ADMIN");
    }

    private void addRoleIfNotExists(String roleName) {
        roleRepo.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            return roleRepo.save(role);
        });
    }

    private void initializeAdminUser() {
        if (userRepo.findByEmail("admin@example.com") == null) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setPasswordHash(passwordEncoder.encode("admin_password"));

            Role adminRole = roleRepo.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Роль ROLE_ADMIN не найдена!"));

            admin.setRoles(Collections.singleton(adminRole));
            userRepo.save(admin);
        }
    }



    private void initializeSoftSkillsData() {
        SoftSkillCategory communicationCategory = categorySoftSkillRepo.findByName("Communication")
                .orElseGet(() -> {
                    SoftSkillCategory category = new SoftSkillCategory();
                    category.setName("Communication");
                    return categorySoftSkillRepo.save(category);
                });

        SoftSkill activeListening = softSkillRepo.findByName("Active Listening")
                .orElseGet(() -> {
                    SoftSkill skill = new SoftSkill();
                    skill.setName("Active Listening");
                    skill.setCategory(communicationCategory);
                    return softSkillRepo.save(skill);
                });

        // Добавление индикаторов для "Active Listening"
        addIndicatorIfNotExists(activeListening, "Pays attention to speaker");
        addIndicatorIfNotExists(activeListening, "Doesn't interrupt");

        // Можно добавить другие soft skills и их индикаторы аналогичным образом
    }

    private void addIndicatorIfNotExists(SoftSkill softSkill, String indicatorName) {
        softSkillIndicatorRepo.findByNameAndSoftSkill(indicatorName, softSkill).orElseGet(() -> {
            SoftSkillIndicator indicator = new SoftSkillIndicator();
            indicator.setName(indicatorName);
            indicator.setSoftSkill(softSkill);
            return softSkillIndicatorRepo.save(indicator);
        });
    }
}

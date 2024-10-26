package com.t1.profile.config;

import com.t1.profile.model.Role;
import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.SoftSkillCategory;
import com.t1.profile.model.SoftSkillIndicator;
import com.t1.profile.repository.RoleRepo;
import com.t1.profile.repository.CategorySoftSkillRepo;
import com.t1.profile.repository.SoftSkillRepo;
import com.t1.profile.repository.SoftSkillIndicatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
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

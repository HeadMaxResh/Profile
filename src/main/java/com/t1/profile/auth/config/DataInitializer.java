package com.t1.profile.auth.config;

import com.t1.profile.user.model.Role;
import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.RoleRepo;
import com.t1.profile.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Создание роли ROLE_USER, если она не существует
        if (roleRepo.findByName("ROLE_USER") == null) {
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepo.save(roleUser);
        }

        // Создание роли ROLE_ADMIN, если она не существует
        if (roleRepo.findByName("ROLE_ADMIN") == null) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepo.save(roleAdmin);
        }

        // Создание пользователя-администратора, если он не существует
        if (userRepo.findByEmail("admin@admin.admin") == null) {
            User adminUser = new User();
            adminUser.setEmail("admin@admin.admin");
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setPasswordHash(passwordEncoder.encode("123456"));

            Role adminRole = roleRepo.findByName("ROLE_ADMIN");
            adminUser.setRoles(Collections.singleton(adminRole));

            userRepo.save(adminUser);
        }
    }
}

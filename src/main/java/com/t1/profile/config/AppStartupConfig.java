package com.t1.profile.config;

import com.t1.profile.model.User;
import com.t1.profile.model.Role;
import com.t1.profile.repository.RoleRepo;
import com.t1.profile.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;

@Configuration
public class AppStartupConfig {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void createAdminUser() {
        String adminEmail = "admin.user@example.com";

        // Проверяем, если роль администратора уже существует
        Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> {
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            return roleRepo.save(role);  // Создаем роль, если её нет
        });

        // Проверяем, если администратор уже существует
        if (userRepo.findByEmail(adminEmail) == null) {
            User adminUser = new User();
            adminUser.setEmail(adminEmail);
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setCity("New York");
            adminUser.setGender("M");
            adminUser.setDateOfBirth(LocalDate.parse("1980-01-01"));
            adminUser.setPasswordHash(passwordEncoder.encode("123456"));

            // Назначаем роль администратора
            adminUser.setRoles(Collections.singleton(adminRole));

            userRepo.save(adminUser);
            System.out.println("Администратор успешно создан!");
        } else {
            System.out.println("Администратор уже существует!");
        }
    }
}

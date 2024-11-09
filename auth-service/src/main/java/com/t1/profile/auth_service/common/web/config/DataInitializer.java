package com.t1.profile.auth_service.common.web.config;

import com.t1.profile.auth_service.model.Role;
import com.t1.profile.auth_service.model.User;
import com.t1.profile.auth_service.repository.RoleRepo;
import com.t1.profile.auth_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static com.t1.profile.auth_service.RoleType.ADMIN;
import static com.t1.profile.auth_service.RoleType.USER;

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
        if (roleRepo.findByName(USER) == null) {
            Role roleUser = new Role();
            roleUser.setName(USER);
            roleRepo.save(roleUser);
        }

        if (roleRepo.findByName(ADMIN) == null) {
            Role roleAdmin = new Role();
            roleAdmin.setName(ADMIN);
            roleRepo.save(roleAdmin);
        }

        if (userRepo.findByEmail("admin@admin.admin") == null) {
            User adminUser = new User();
            adminUser.setEmail("admin@admin.admin");
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setPasswordHash(passwordEncoder.encode("123456"));

            Role adminRole = roleRepo.findByName(ADMIN);
            adminUser.setRoles(Collections.singleton(adminRole));

            userRepo.save(adminUser);
        }
    }

}

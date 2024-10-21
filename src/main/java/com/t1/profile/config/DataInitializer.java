package com.t1.profile.config;

import com.t1.profile.model.Role;
import com.t1.profile.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepo.findByName("ROLE_USER") == null) {
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepo.save(roleUser);
        }

        if (roleRepo.findByName("ROLE_ADMIN") == null) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepo.save(roleAdmin);
        }
    }
}

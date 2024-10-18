package com.t1.profile;

import com.t1.profile.model.Role;
import com.t1.profile.model.User;
import com.t1.profile.repository.RoleRepository;
import com.t1.profile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Создаем роли без префиксов ROLE_
        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

        Role adminRole = roleRepository.findByName("ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        // Создаем пользователя, если его нет
        if (!userRepository.findByEmail("ivanov@example.com").isPresent()) {
            User user = new User();
            user.setFirstName("Иван");
            user.setLastName("Иванов");
            user.setEmail("ivanov@example.com");
            user.setPasswordHash(passwordEncoder.encode("password"));
            user.setDateOfBirth(LocalDate.of(1999, 1, 1));
            user.setCity("Москва");
            user.setGender("M");

            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);

            userRepository.save(user);
        }

        // Создаем администратора, если его нет
        if (!userRepository.findByEmail("admin@example.com").isPresent()) {
            User admin = new User();
            admin.setFirstName("Админ");
            admin.setLastName("Администраторов");
            admin.setEmail("admin@example.com");
            admin.setPasswordHash(passwordEncoder.encode("adminpassword"));
            admin.setDateOfBirth(LocalDate.of(1995, 5, 15));
            admin.setCity("Санкт-Петербург");
            admin.setGender("F");

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(adminRole);
            admin.setRoles(adminRoles);

            userRepository.save(admin);
        }
    }
}



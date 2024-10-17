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

        // Создаем роли, если их еще нет
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        // Проверяем, существует ли пользователь с таким email
        if (!userRepository.findByEmail("ivanov@example.com").isPresent()) {
            // Создаем пользователя
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

        // Проверяем, существует ли администратор с таким email
        if (!userRepository.findByEmail("admin@example.com").isPresent()) {
            // Создаем администратора
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

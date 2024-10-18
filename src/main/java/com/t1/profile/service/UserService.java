package com.t1.profile.service;

import com.t1.profile.dto.UserRegistrationDto;
import com.t1.profile.model.Role;
import com.t1.profile.model.User;
import com.t1.profile.repository.RoleRepository;
import com.t1.profile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(UserRegistrationDto registrationDto) {
        // Проверяем, существует ли пользователь с таким email
        if (userRepository.findByEmail(registrationDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        // Создаем нового пользователя
        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));

        // Устанавливаем роль
        Role userRole = roleRepository.findByName("USER"); // Ищем роль 'USER'
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("USER"); // Создаем роль 'USER'
            roleRepository.save(userRole);
        }
        user.setRoles(Collections.singleton(userRole));

        // Сохраняем пользователя в базе данных
        return userRepository.save(user);
    }

}

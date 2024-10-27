package com.t1.profile.service;

import com.t1.profile.dto.*;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.Profession;
import com.t1.profile.model.Role;
import com.t1.profile.model.User;
import com.t1.profile.repository.RoleRepo;
import com.t1.profile.repository.ProfessionRepo;
import com.t1.profile.repository.UserRepo;
import com.t1.profile.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final ProfessionRepo professionRepo;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepo userRepo, RoleRepo roleRepo,
                           PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, ProfessionRepo professionRepo) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.professionRepo = professionRepo;
    }

    @Override
    public ApiDto registerUser(RegistrationDto registrationDto) {
        logger.info("Регистрация нового пользователя с email: {}", registrationDto.getEmail());

        // Проверка на дублирование email
        if (userRepo.findByEmail(registrationDto.getEmail()) != null) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        // Проверка и парсинг даты рождения
        LocalDate dateOfBirth;
        if (registrationDto.getDateOfBirth() != null && !registrationDto.getDateOfBirth().isEmpty()) {
            try {
                dateOfBirth = LocalDate.parse(registrationDto.getDateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Дата рождения должна быть в формате yyyy-MM-dd");
            }
        } else {
            throw new IllegalArgumentException("Дата рождения не может быть пустой");
        }

        // Создаем пользователя
        User user = new User();
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setEmail(registrationDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        user.setCity(registrationDto.getCity());
        user.setGender(registrationDto.getGender());
        user.setDateOfBirth(dateOfBirth);

        // Назначаем профессию, если указана
        if (registrationDto.getProfessionId() != null) {
            Profession profession = professionRepo.findById(registrationDto.getProfessionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profession not found with id " + registrationDto.getProfessionId()));
            user.setProfession(profession);
        } else {
            throw new IllegalArgumentException("Профессия не может быть пустой");
        }

        // Назначаем роль пользователя
        Role userRole = roleRepo.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newUserRole = new Role();
                    newUserRole.setName("ROLE_USER");
                    return roleRepo.save(newUserRole);
                });
        user.setRoles(Collections.singleton(userRole));

        userRepo.save(user);
        return new ApiDto(true, "User registered successfully");
    }


    @Override
    public JwtAuthenticationDto authenticateUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        User user = userRepo.findByEmail(loginDto.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + loginDto.getEmail());
        }

        UserDto userDto = new UserDto(user);
        userDto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));

        JwtAuthenticationDto jwtResponse = new JwtAuthenticationDto(token);
        jwtResponse.setUser(userDto);

        return jwtResponse;
    }
}

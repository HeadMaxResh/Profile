package com.t1.profile.service;

import com.t1.profile.RoleType;
import com.t1.profile.dto.*;
import com.t1.profile.model.Role;
import com.t1.profile.model.User;
import com.t1.profile.repository.RoleRepo;
import com.t1.profile.repository.UserRepo;
import com.t1.profile.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepo userRepo, RoleRepo roleRepo,
                           PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public ApiDto registerUser(RegistrationDto registrationDto) {
        if (userRepo.findByEmail(registrationDto.getEmail()) != null) {
            return new ApiDto(false, "Email Address already in use!");
        }

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));

        // Используем orElseThrow для Optional<Role>
        Role userRole = roleRepo.findByName(RoleType.USER)
                .orElseThrow(() -> new RuntimeException("Role USER not found!"));
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

        // Проверка существования пользователя
        User user = userRepo.findByEmail(loginDto.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + loginDto.getEmail());
        }

        UserDto userDto = new UserDto(user);
        JwtAuthenticationDto jwtResponse = new JwtAuthenticationDto(token);
        jwtResponse.setUser(userDto);

        return jwtResponse;
    }
}

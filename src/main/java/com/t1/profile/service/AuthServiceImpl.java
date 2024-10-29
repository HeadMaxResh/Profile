package com.t1.profile.service;

import com.t1.profile.dto.ApiDto;
import com.t1.profile.dto.JwtAuthenticationDto;
import com.t1.profile.dto.LoginDto;
import com.t1.profile.dto.RegistrationDto;
import com.t1.profile.model.Role;
import com.t1.profile.model.User;
import com.t1.profile.repository.RoleRepo;
import com.t1.profile.repository.UserRepo;
import com.t1.profile.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
// Импортируйте необходимые пакеты
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public ApiDto registerUser(RegistrationDto registrationDto) {
        if (userRepo.findByEmail(registrationDto.getEmail()) != null) {
            return new ApiDto(false, "Email уже используется!");
        }

        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setFirstName(registrationDto.getFirstName());
        user.setLastName(registrationDto.getLastName()); // Убедитесь, что lastName установлен
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));

        Role userRole = roleRepo.findByName("ROLE_USER"); // Убедитесь, что роль существует
        if (userRole == null) {
            userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepo.save(userRole);
        }
        user.setRoles(Collections.singleton(userRole));

        userRepo.save(user);

        return new ApiDto(true, "Пользователь успешно зарегистрирован");
    }

    @Override
    public JwtAuthenticationDto authenticateUser(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return new JwtAuthenticationDto(jwt);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неверный email или пароль");
        }
    }
}

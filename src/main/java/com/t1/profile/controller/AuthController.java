package com.t1.profile.controller;

import com.t1.profile.dto.ApiDto;
import com.t1.profile.dto.JwtAuthenticationDto;
import com.t1.profile.dto.LoginDto;
import com.t1.profile.dto.RegistrationDto;
import com.t1.profile.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PreAuthorize("hasRole('ADMIN')") //добавил
    @PostMapping("/register")
    public ResponseEntity<ApiDto> registerUser(@RequestBody RegistrationDto registrationDto) {
        System.out.println("Полученная дата рождения: " + registrationDto.getDateOfBirth());
        ApiDto response = authService.registerUser(registrationDto);
        HttpStatus status = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationDto> authenticateUser(@RequestBody LoginDto loginDto) {
        JwtAuthenticationDto jwtResponse = authService.authenticateUser(loginDto);
        return ResponseEntity.ok(jwtResponse);
    }
}

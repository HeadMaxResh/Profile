package com.t1.profile.controller;

import com.t1.profile.dto.ApiDto;
import com.t1.profile.dto.JwtAuthenticationDto;
import com.t1.profile.dto.LoginDto;
import com.t1.profile.dto.RegistrationDto;
import com.t1.profile.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDto registrationDto) {
        ApiDto response = authService.registerUser(registrationDto);

        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        JwtAuthenticationDto jwtResponse = authService.authenticateUser(loginDto);
        return ResponseEntity.ok(jwtResponse);
    }
}

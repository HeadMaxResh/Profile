package com.t1.profile.controller;

import com.t1.profile.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil tokenProvider;

    @PostMapping("/login")
    public String authenticateUser(@RequestParam String email, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return tokenProvider.generateToken(userDetails.getUsername());
    }
}

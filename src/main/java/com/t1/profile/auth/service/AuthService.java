package com.t1.profile.auth.service;

import com.t1.profile.auth.dto.ApiDto;
import com.t1.profile.auth.dto.JwtAuthenticationDto;
import com.t1.profile.auth.dto.LoginDto;
import com.t1.profile.auth.dto.RegistrationDto;

public interface AuthService {

    ApiDto registerUser(RegistrationDto registrationDto);
    JwtAuthenticationDto authenticateUser(LoginDto loginDto);

}

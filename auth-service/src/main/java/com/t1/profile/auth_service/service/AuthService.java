package com.t1.profile.auth_service.service;

import com.t1.profile.auth_service.dto.ApiDto;
import com.t1.profile.auth_service.dto.JwtAuthenticationDto;
import com.t1.profile.auth_service.dto.LoginDto;
import com.t1.profile.auth_service.dto.RegistrationDto;

public interface AuthService {

    ApiDto registerUser(RegistrationDto registrationDto);
    JwtAuthenticationDto authenticateUser(LoginDto loginDto);

}

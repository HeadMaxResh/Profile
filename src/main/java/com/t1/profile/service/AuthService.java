package com.t1.profile.service;

import com.t1.profile.dto.ApiDto;
import com.t1.profile.dto.JwtAuthenticationDto;
import com.t1.profile.dto.LoginDto;
import com.t1.profile.dto.RegistrationDto;

public interface AuthService {

    ApiDto registerUser(RegistrationDto registrationDto);
    JwtAuthenticationDto authenticateUser(LoginDto loginDto);

}

package com.t1.profile.service;

import com.t1.profile.dto.*;

public interface AuthService {

    ApiDto registerUser(RegistrationDto registrationDto);
    JwtAuthenticationDto authenticateUser(LoginDto loginDto);

}

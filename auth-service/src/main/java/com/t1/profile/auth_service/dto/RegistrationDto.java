package com.t1.profile.auth_service.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class RegistrationDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private LocalDate birthDate;
    @NotBlank
    private String gender;
    @NotBlank
    private String city;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;
}
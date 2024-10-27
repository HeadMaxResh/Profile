package com.t1.profile.dto;

// File: RegistrationDto.java

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class RegistrationDto {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;

    @NotBlank
    private String city; //добавил

    @NotBlank
    private String gender;//добавил

    @NotBlank
    private String dateOfBirth;//добавил

    @NotNull
    private Integer professionId;//добавил
}

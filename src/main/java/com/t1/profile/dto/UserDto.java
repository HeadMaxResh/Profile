package com.t1.profile.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String city;
    private String email;
    private ProfessionDto profession;
    private Set<UserHardSkillDto> userHardSkills;
    private String photoPath; // Для фото (путь)
}

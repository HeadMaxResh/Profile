package com.t1.profile.dto;

import com.t1.profile.model.Profession;
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
    private Profession profession;
    private Set<HardSkillDto> hardSkills;
}

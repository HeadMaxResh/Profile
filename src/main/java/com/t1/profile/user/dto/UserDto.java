package com.t1.profile.user.dto;

import com.t1.profile.skill.hard.dto.UserHardSkillDto;
import com.t1.profile.profession.dto.ProfessionDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String city;
    private String email;
    private ProfessionDto profession;
    private Set<UserHardSkillDto> userHardSkills;
}

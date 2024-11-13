package com.t1.profile.user.dto;

import com.t1.profile.profession.dto.ProfessionDto;
import com.t1.profile.user.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDetailsDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private ProfessionDto profession;
    private String email;
    private String passwordHash;
    private Set<Role> roles;

}

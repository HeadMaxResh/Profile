package com.t1.profile.review_service.dto;

import com.t1.profile.review_service.model.Role;
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

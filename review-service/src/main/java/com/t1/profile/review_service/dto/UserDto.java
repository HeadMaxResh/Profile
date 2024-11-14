package com.t1.profile.review_service.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private ProfessionDto profession;
    private String email;


}

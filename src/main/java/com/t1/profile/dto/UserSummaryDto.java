package com.t1.profile.dto;

import lombok.Data;

@Data
public class UserSummaryDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private ProfessionDto profession;

}

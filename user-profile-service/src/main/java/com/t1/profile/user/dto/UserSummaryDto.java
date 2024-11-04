package com.t1.profile.user.dto;

import com.t1.profile.profession.dto.ProfessionDto;
import lombok.Data;

@Data
public class UserSummaryDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private ProfessionDto profession;

}

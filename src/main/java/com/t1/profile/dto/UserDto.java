package com.t1.profile.dto;

import com.t1.profile.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String city;
    private String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dateOfBirth = user.getDateOfBirth();
        this.gender = user.getGender();
        this.city = user.getCity();
        this.email = user.getEmail();
    }
}

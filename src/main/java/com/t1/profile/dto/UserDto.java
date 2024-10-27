package com.t1.profile.dto;

import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.model.Role;
import com.t1.profile.model.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
//обновлен класс
@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String dateOfBirth; // исправил на String
    private String gender;
    private String city;
    private String email;
    private Profession profession;
    private Set<HardSkill> hardSkills;
    private Set<String> roles; // Добавлено поле для ролей

    public UserDto(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // проверка даты рождения на null перед форматированием
        if (user.getDateOfBirth() != null) {
            this.dateOfBirth = user.getDateOfBirth().format(formatter);
        } else {
            this.dateOfBirth = null;
        }

        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();

        this.gender = user.getGender();
        this.city = user.getCity();
        this.email = user.getEmail();
        this.profession = user.getProfession();
        this.hardSkills = user.getHardSkills();

        // Инициализация ролей пользователя
        this.roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());


    }
}

package com.t1.profile.auth_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity(name = "table_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String city;
    private String gender;
    @Column(unique = true, nullable = false)
    private String email;
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
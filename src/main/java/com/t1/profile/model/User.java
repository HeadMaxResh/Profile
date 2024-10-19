package com.t1.profile.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users") // Избегаем использования зарезервированных слов
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String password;
    private String name;

    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // Assuming you have a user_roles table
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "user_main_hard_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hard_skill_id")
    )
    private Set<HardSkill> mainHardSkills = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_additional_hard_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hard_skill_id")
    )
    private Set<HardSkill> additionalHardSkills = new HashSet<>();

    // If you're not using Lombok's @Data, add the missing setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

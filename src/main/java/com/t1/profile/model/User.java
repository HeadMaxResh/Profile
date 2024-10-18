package com.t1.profile.model;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.model.Role;
import com.t1.profile.model.SoftSkill;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "table_user") // Указываем имя таблицы
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
    private String city;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    // Поле для связи с объектом Role
    @ManyToMany(fetch = FetchType.EAGER)  // Множественная связь Many-to-Many
    @JoinTable(
            name = "user_roles",  // Таблица для связи пользователей с ролями
            joinColumns = @JoinColumn(name = "user_id"),  // Связь через поле user_id
            inverseJoinColumns = @JoinColumn(name = "role_id")  // Связь через поле role_id
    )
    private Set<Role> roles = new HashSet<>();  // Коллекция ролей для пользователя

    // Дополнительные поля (HardSkills и SoftSkills) и связи
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<HardSkill> mainHardSkills = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<HardSkill> additionalHardSkills = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SoftSkill> softSkills = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;
}

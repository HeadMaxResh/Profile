package com.t1.profile.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class SoftSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SoftSkillCategory category;

    @OneToMany(mappedBy = "softSkill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SoftSkillIndicator> indicators = new HashSet<>();

    // Конструкторы, геттеры и сеттеры
}

package com.t1.profile.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class HardSkillCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<HardSkill> hardSkills = new HashSet<>();

    // геттеры и сеттеры
}


package com.t1.profile.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class SoftSkillCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SoftSkill> softSkills = new HashSet<>();
}

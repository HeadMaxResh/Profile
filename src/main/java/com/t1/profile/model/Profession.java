package com.t1.profile.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Profession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "profession", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<HardSkill> mainHardSkills = new HashSet<>();
}

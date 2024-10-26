package com.t1.profile.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SoftSkillIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "soft_skill_id")
    private SoftSkill softSkill;

    // Конструкторы, геттеры и сеттеры
}

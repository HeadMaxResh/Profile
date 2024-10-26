package com.t1.profile.model;


import jakarta.persistence.*;

@Entity
@Table(name = "hard_skill_indicators")
public class HardSkillIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)  // Связь с HardSkill
    @JoinColumn(name = "hard_skill_id", nullable = false)
    private HardSkill hardSkill;

    // Конструкторы
    public HardSkillIndicator() {}

    public HardSkillIndicator(String name, HardSkill hardSkill) {
        this.name = name;
        this.hardSkill = hardSkill;
    }

    // Геттеры и сеттеры
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HardSkill getHardSkill() {
        return hardSkill;
    }

    public void setHardSkill(HardSkill hardSkill) {
        this.hardSkill = hardSkill;
    }
}


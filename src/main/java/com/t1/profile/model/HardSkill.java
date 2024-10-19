package com.t1.profile.model;

import com.t1.profile.enums.HardSkillType;
import jakarta.persistence.*;

@Entity
public class HardSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private HardSkillType type;

    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;

    // Конструкторы
    public HardSkill() {
    }

    public HardSkill(String name, HardSkillType type, Profession profession) {
        this.name = name;
        this.type = type;
        this.profession = profession;
    }

    // Геттеры и Сеттеры
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HardSkillType getType() {
        return type;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(HardSkillType type) {
        this.type = type;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}

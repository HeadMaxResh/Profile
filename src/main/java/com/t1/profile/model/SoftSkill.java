package com.t1.profile.model;

import com.t1.profile.enums.SkillLevel;
import com.t1.profile.Table;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class SoftSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private SkillLevel level;

    @ManyToOne
    @JoinColumn(name = Table.USER_ID)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SoftSkillCategory category;
}

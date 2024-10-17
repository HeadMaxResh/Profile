package com.t1.profile.model;

import com.t1.profile.enums.HardSkillType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class HardSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private HardSkillType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

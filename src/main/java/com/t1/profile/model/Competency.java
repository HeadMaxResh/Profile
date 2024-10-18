package com.t1.profile.model;

import com.t1.profile.enums.CompetencyType;
import com.t1.profile.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Competency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String name;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private CompetencyType type;

    @Enumerated(EnumType.STRING)
    private SkillLevel level;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Competency parent;
}

package com.t1.profile.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SoftSkillRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "soft_skill_id")
    private SoftSkill softSkill;

    @ManyToOne
    @JoinColumn(name = "rated_user_id")
    private User ratedUser;

    @ManyToOne
    @JoinColumn(name = "rater_user_id")
    private User raterUser;

    private Integer rating;

}

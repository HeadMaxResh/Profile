package com.t1.profile.skill.soft.dto;

import lombok.Data;

@Data
public class SoftSkillWithAverageRatingDto {

    private Integer id;
    private String name;
    private Double averageRating;
    private Double historyRating;

}
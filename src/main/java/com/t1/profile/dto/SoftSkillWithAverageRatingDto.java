package com.t1.profile.dto;

import lombok.Data;

@Data
public class SoftSkillWithAverageRatingDto {

    private Integer id;
    private String name;
    private Double averageRating;

}
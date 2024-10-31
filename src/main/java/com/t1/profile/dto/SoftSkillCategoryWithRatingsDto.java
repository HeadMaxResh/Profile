package com.t1.profile.dto;

import lombok.Data;

import java.util.List;

@Data
public class SoftSkillCategoryWithRatingsDto {

    private Integer id;
    private String name;
    private List<SoftSkillWithAverageRatingDto> softSkills;

}
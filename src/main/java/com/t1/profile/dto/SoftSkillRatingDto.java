package com.t1.profile.dto;

import lombok.Data;

@Data
public class SoftSkillRatingDto {

    private Integer softSkillId;
    private Integer ratedUserId;
    private Integer raterUserId;
    private Integer rating;

}

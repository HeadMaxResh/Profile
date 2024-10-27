package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillRatingDto;

import java.util.List;

public interface UserSoftSkillService {

    SoftSkillRatingDto rateSoftSkill(SoftSkillRatingDto ratingDto);
    List<SoftSkillRatingDto> getRatingBySoftSkill(Integer softSkillId);

}

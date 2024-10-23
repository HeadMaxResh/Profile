package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillRatingDto;
import com.t1.profile.model.SoftSkillRating;

import java.util.List;

public interface UserSoftSkillService {

    SoftSkillRating rateSoftSkill(SoftSkillRatingDto ratingDto);
    List<SoftSkillRating> getRatingBySoftSkill(Integer softSkillId);

}

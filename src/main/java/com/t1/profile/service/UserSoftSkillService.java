package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryWithRatingsDto;
import com.t1.profile.dto.UserSoftSkillDto;

import java.util.List;

public interface UserSoftSkillService {

    UserSoftSkillDto rateSoftSkill(UserSoftSkillDto ratingDto);
    List<UserSoftSkillDto> getRatingBySoftSkill(Integer softSkillId);
    List<SoftSkillCategoryWithRatingsDto> getSoftSkillsWithRatingsByUser(Integer userId);

}

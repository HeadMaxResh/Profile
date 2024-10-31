package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryWithRatingsDto;
import com.t1.profile.dto.UserSoftSkillResponseDto;
import com.t1.profile.dto.UserSoftSkillRequestDto;

import java.util.List;

public interface UserSoftSkillService {

    UserSoftSkillResponseDto rateSoftSkill(UserSoftSkillRequestDto ratingDto);
    List<UserSoftSkillResponseDto> getRatingBySoftSkill(Integer softSkillId);
    List<SoftSkillCategoryWithRatingsDto> getSoftSkillsWithRatingsByUser(Integer userId);

}

package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryWithRatingsDto;
import com.t1.profile.dto.UserSoftSkillBatchRequestDto;
import com.t1.profile.dto.UserSoftSkillRequestDto;
import com.t1.profile.dto.UserSoftSkillResponseDto;

import java.util.List;

public interface UserSoftSkillService {

    UserSoftSkillResponseDto rateSoftSkill(UserSoftSkillRequestDto ratingDto);
    List<UserSoftSkillResponseDto> rateMultipleSoftSkills(UserSoftSkillBatchRequestDto batchRequestDto);
    List<UserSoftSkillResponseDto> getRatingBySoftSkill(Integer softSkillId);
    List<SoftSkillCategoryWithRatingsDto> getSoftSkillsWithRatingsByUser(Integer userId);
    void deleteUserSoftSkill(Integer userSoftSkillId);
    void deleteAllUserSoftSkillsByUserId(Integer userId);

}

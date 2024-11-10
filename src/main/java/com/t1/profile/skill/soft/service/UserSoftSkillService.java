package com.t1.profile.skill.soft.service;

import com.t1.profile.skill.soft.dto.SoftSkillCategoryWithRatingsDto;
import com.t1.profile.skill.soft.dto.UserSoftSkillBatchRequestDto;
import com.t1.profile.skill.soft.dto.UserSoftSkillRequestDto;
import com.t1.profile.skill.soft.dto.UserSoftSkillResponseDto;

import java.util.List;

public interface UserSoftSkillService {

    UserSoftSkillResponseDto rateSoftSkill(UserSoftSkillRequestDto ratingDto);
    List<UserSoftSkillResponseDto> rateMultipleSoftSkills(UserSoftSkillBatchRequestDto batchRequestDto);
    List<UserSoftSkillResponseDto> getRatingBySoftSkill(Integer softSkillId);

    List<SoftSkillCategoryWithRatingsDto> getSoftSkillsWithRatingsByUser(Integer userId);

    void deleteUserSoftSkill(Integer userSoftSkillId);

    void deleteAllUserSoftSkillsByUserId(Integer userId);
}

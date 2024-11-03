package com.t1.profile.skill.soft.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserSoftSkillBatchRequestDto {

    private List<UserSoftSkillRequestDto> ratings;

}
package com.t1.profile.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserSoftSkillBatchRequestDto {
    private List<UserSoftSkillRequestDto> ratings;
}
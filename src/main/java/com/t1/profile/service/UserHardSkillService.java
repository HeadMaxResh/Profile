package com.t1.profile.service;

import com.t1.profile.dto.UserDto;
import com.t1.profile.dto.UserHardSkillsDto;

public interface UserHardSkillService {

    UserDto addHardSkillToUser(Integer userId, Integer hardSkillId);
    void removeHardSkillFromUser(Integer userId, Integer hardSkillId);
    UserHardSkillsDto getUserAndProfessionHardSkills(Integer userId);
    UserHardSkillsDto getUserAndProfessionHardSkills(Integer userId, Integer professionId);

}

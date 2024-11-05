package com.t1.profile.skill.hard.service;

import com.t1.profile.user.dto.UserDto;
import com.t1.profile.skill.hard.dto.UserHardSkillDto;
import com.t1.profile.skill.hard.dto.UserHardSkillsCategorizedDto;

import java.util.Set;

public interface UserHardSkillService {

    Set<UserHardSkillDto> getHardSkillsByUser(Long userId);
    UserDto addHardSkillToUser(Long userId, Long hardSkillId, Integer rating);
    UserHardSkillDto updateHardSkillRating(Long userId, Long hardSkillId, Integer newRating);
    UserHardSkillDto updateHardSkillRating(Long userHardSkillId, Integer newRating);
    void removeHardSkillFromUser(Long userId, Long hardSkillId);
    void removeHardSkillFromUser(Long userHardSkillId);
    UserHardSkillsCategorizedDto getUserAndProfessionHardSkills(Long userId);
    UserHardSkillsCategorizedDto getUserAndProfessionHardSkills(Long userId, Long professionId);

}

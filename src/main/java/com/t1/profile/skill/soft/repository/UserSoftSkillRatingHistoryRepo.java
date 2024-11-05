package com.t1.profile.skill.soft.repository;

import com.t1.profile.skill.soft.model.SoftSkill;
import com.t1.profile.user.model.User;
import com.t1.profile.skill.soft.model.UserSoftSkillRatingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserSoftSkillRatingHistoryRepo extends
    JpaRepository<UserSoftSkillRatingHistory, Integer> {

    List<UserSoftSkillRatingHistory> findByRatedUserAndSoftSkill(User ratedUser, SoftSkill softSkill);

}

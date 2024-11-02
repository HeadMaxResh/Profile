package com.t1.profile.repository;

import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.User;
import com.t1.profile.model.UserSoftSkillRatingHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserSoftSkillRatingHistoryRepo extends CrudRepository<UserSoftSkillRatingHistory, Integer> {

    List<UserSoftSkillRatingHistory> findByRatedUserAndSoftSkill(User ratedUser, SoftSkill softSkill);

}

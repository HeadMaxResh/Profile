package com.t1.profile.skill.soft.repository;

import com.t1.profile.skill.soft.model.SoftSkill;
import com.t1.profile.user.model.User;
import com.t1.profile.skill.soft.model.UserSoftSkillRating;
import org.springframework.data.repository.CrudRepository;

public interface UserSoftSkillRatingRepo extends CrudRepository<UserSoftSkillRating, Integer> {

    UserSoftSkillRating findByRatedUserAndSoftSkill(User ratedUser, SoftSkill softSkill);

}

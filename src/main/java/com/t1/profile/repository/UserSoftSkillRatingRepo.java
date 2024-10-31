package com.t1.profile.repository;

import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.User;
import com.t1.profile.model.UserSoftSkillRating;
import org.springframework.data.repository.CrudRepository;

public interface UserSoftSkillRatingRepo extends CrudRepository<UserSoftSkillRating, Integer> {

    UserSoftSkillRating findByRatedUserAndSoftSkill(User ratedUser, SoftSkill softSkill);

}

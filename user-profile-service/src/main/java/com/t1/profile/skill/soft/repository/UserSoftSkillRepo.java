package com.t1.profile.skill.soft.repository;

import com.t1.profile.user.model.User;
import com.t1.profile.skill.soft.model.UserSoftSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSoftSkillRepo extends CrudRepository<UserSoftSkill, Integer> {

    @Query("SELECT s FROM UserSoftSkill s WHERE s.softSkill.id = :softSkillId")
    List<UserSoftSkill> findRatingsBySoftSkillId(@Param("softSkillId") Integer softSkillId);

    @Query("SELECT r FROM UserSoftSkill r WHERE r.ratedUser.id = :ratedUserId")
    List<UserSoftSkill> findByRatedUserId(@Param("ratedUserId") Integer ratedUserId);

    @Query("SELECT r FROM UserSoftSkill r WHERE r.raterUser.id = :raterUserId")
    List<UserSoftSkill> findByRaterId(@Param("raterUserId") Integer raterUserId);

    @Query("SELECT AVG(r.rating) FROM UserSoftSkill r WHERE r.ratedUser.id = :ratedUserId AND r.softSkill.id = :softSkillId")
    Double findAverageRatingByUserAndSoftSkill(
            @Param("ratedUserId") Integer ratedUserId,
            @Param("softSkillId") Integer softSkillId
    );

    List<UserSoftSkill> findByRatedUser(User user);

}

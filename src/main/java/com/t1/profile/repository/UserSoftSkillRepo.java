package com.t1.profile.repository;

import com.t1.profile.model.SoftSkillRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSoftSkillRepo extends CrudRepository<SoftSkillRating, Integer> {

    @Query("SELECT s FROM SoftSkillRating s WHERE s.softSkill.id = :softSkillId")
    List<SoftSkillRating> findRatingsBySoftSkillId(@Param("softSkillId") Integer softSkillId);

    @Query("SELECT r FROM SoftSkillRating r WHERE r.ratedUser.id = :ratedUserId")
    List<SoftSkillRating> findByRatedUserId(@Param("ratedUserId") Integer ratedUserId);

    @Query("SELECT r FROM SoftSkillRating r WHERE r.rater.id = :raterId")
    List<SoftSkillRating> findByRaterId(@Param("raterId") Integer raterId);

    @Query("SELECT AVG(r.rating) FROM SoftSkillRating r WHERE r.ratedUser.id = :ratedUserId AND r.softSkill.id = :softSkillId")
    Double findAverageRatingByUserAndSoftSkill(
            @Param("ratedUserId") Integer ratedUserId,
            @Param("softSkillId") Integer softSkillId);


}

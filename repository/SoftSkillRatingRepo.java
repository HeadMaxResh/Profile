package com.t1.profile.repository;

import com.t1.profile.model.SoftSkillRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SoftSkillRatingRepo extends CrudRepository<SoftSkillRating, Integer> {

    @Query("SELECT s FROM SoftSkillRating s WHERE s.softSkill.id = :softSkillId")
    List<SoftSkillRating> findRatingsBySoftSkillId(@Param("softSkillId") Integer softSkillId);
}

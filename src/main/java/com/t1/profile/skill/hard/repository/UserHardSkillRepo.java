package com.t1.profile.skill.hard.repository;

import com.t1.profile.skill.hard.model.UserHardSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserHardSkillRepo extends JpaRepository<UserHardSkill, Long> {

    @Query("SELECT uhs FROM UserHardSkill uhs WHERE uhs.user.id = :userId")
    List<UserHardSkill> findByUserId(Long userId);

}

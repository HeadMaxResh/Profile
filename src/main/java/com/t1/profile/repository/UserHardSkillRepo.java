package com.t1.profile.repository;

import com.t1.profile.model.UserHardSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserHardSkillRepo extends CrudRepository<UserHardSkill, Integer> {

    @Query("SELECT uhs FROM UserHardSkill uhs WHERE uhs.user.id = :userId")
    List<UserHardSkill> findByUserId(Integer userId);

}

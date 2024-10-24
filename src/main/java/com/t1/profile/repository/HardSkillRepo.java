package com.t1.profile.repository;

import com.t1.profile.model.HardSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HardSkillRepo extends CrudRepository<HardSkill, Integer> {

    @Query("SELECT h FROM HardSkill h WHERE h.user.id = :userId")
    List<HardSkill> findByUserId(Integer userId);

    @Query("SELECT h FROM HardSkill h WHERE h.profession.id = :professionId")
    List<HardSkill> findByProfessionId(Integer professionId);

}

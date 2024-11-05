package com.t1.profile.skill.hard.repository;

import com.t1.profile.skill.hard.model.HardSkill;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HardSkillRepo extends JpaRepository<HardSkill, Long> {

    @Query("SELECT h FROM HardSkill h WHERE h.profession.id = :professionId")
    List<HardSkill> findByProfessionId(Long professionId);
}

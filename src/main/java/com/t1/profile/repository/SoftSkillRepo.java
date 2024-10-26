package com.t1.profile.repository;

import com.t1.profile.model.SoftSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SoftSkillRepo extends JpaRepository<SoftSkill, Integer> {
    Optional<SoftSkill> findByName(String name);
}

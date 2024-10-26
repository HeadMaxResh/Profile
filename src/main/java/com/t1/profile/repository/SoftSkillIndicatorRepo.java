package com.t1.profile.repository;

import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.SoftSkillIndicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SoftSkillIndicatorRepo extends JpaRepository<SoftSkillIndicator, Integer> {
    Optional<SoftSkillIndicator> findByNameAndSoftSkill(String name, SoftSkill softSkill);
}

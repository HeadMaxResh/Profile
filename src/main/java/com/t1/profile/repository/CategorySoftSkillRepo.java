// CategorySoftSkillRepo.java
package com.t1.profile.repository;

import com.t1.profile.model.SoftSkillCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategorySoftSkillRepo extends JpaRepository<SoftSkillCategory, Integer> {
    Optional<SoftSkillCategory> findByName(String name);
}
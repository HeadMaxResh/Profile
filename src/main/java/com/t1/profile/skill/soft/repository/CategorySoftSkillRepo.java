package com.t1.profile.skill.soft.repository;

import com.t1.profile.skill.soft.model.SoftSkillCategory;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategorySoftSkillRepo extends JpaRepository<SoftSkillCategory, Integer> {

    List<SoftSkillCategory> findAll();

}

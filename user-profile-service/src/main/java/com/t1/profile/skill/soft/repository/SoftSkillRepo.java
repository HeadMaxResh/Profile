package com.t1.profile.skill.soft.repository;

import com.t1.profile.skill.soft.model.SoftSkill;
import com.t1.profile.skill.soft.model.SoftSkillCategory;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SoftSkillRepo extends CrudRepository<SoftSkill, Integer> {

    List<SoftSkill> findAll();

    List<SoftSkill> findByCategory(SoftSkillCategory category);

}

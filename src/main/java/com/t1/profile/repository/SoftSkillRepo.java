package com.t1.profile.repository;

import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.SoftSkillCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SoftSkillRepo extends CrudRepository<SoftSkill, Integer> {

    List<SoftSkill> findByCategory(SoftSkillCategory category);

}

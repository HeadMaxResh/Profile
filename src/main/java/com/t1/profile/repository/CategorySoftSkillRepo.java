package com.t1.profile.repository;

import com.t1.profile.model.SoftSkillCategory;
import org.springframework.data.repository.CrudRepository;

public interface CategorySoftSkillRepo extends CrudRepository<SoftSkillCategory, Integer> {
}

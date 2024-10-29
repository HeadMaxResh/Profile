package com.t1.profile.repository;

import com.t1.profile.model.SoftSkillCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategorySoftSkillRepo extends CrudRepository<SoftSkillCategory, Integer> {

    List<SoftSkillCategory> findAll();

}

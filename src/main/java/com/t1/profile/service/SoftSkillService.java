package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryDto;
import com.t1.profile.dto.SoftSkillDto;
import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.SoftSkillCategory;

public interface SoftSkillService {

    SoftSkillCategory addCategory(SoftSkillCategoryDto categoryDto);
    void deleteCategory(Integer categoryId);
    SoftSkill addSoftSkill(SoftSkillDto softSkillDto);
    void deleteSoftSkill(Integer softSkillId);

}

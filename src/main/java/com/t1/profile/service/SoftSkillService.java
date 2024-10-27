package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryDto;
import com.t1.profile.dto.SoftSkillDto;

public interface SoftSkillService {

    SoftSkillCategoryDto addCategory(SoftSkillCategoryDto categoryDto);
    void deleteCategory(Integer categoryId);
    SoftSkillDto addSoftSkill(SoftSkillDto softSkillDto);
    void deleteSoftSkill(Integer softSkillId);

}

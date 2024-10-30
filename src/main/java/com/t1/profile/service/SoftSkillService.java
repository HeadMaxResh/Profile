package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryDto;
import com.t1.profile.dto.SoftSkillCategoryWithSkillsDto;
import com.t1.profile.dto.SoftSkillDto;

import java.util.List;

public interface SoftSkillService {

    List<SoftSkillCategoryDto> getAllSoftSkillCategory();
    List<SoftSkillDto> getSoftSkillsByCategory(Integer categoryId);
    List<SoftSkillCategoryWithSkillsDto> getAllSoftSkillCategoriesWithSkills();
    SoftSkillCategoryDto addCategory(SoftSkillCategoryDto categoryDto);
    void deleteCategory(Integer categoryId);
    SoftSkillDto addSoftSkill(Integer categoryId, SoftSkillDto softSkillDto);
    void deleteSoftSkill(Integer softSkillId);

}

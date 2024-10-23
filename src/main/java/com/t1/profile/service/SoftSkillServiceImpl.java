package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryDto;
import com.t1.profile.dto.SoftSkillDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.SoftSkillCategory;
import com.t1.profile.repository.CategorySoftSkillRepo;
import com.t1.profile.repository.SoftSkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoftSkillServiceImpl implements SoftSkillService {

    @Autowired
    private SoftSkillRepo softSkillRepo;

    @Autowired
    private CategorySoftSkillRepo categorySoftSkillRepo;

    @Override
    public SoftSkillCategory addCategory(SoftSkillCategoryDto categoryDto) {
        SoftSkillCategory category = new SoftSkillCategory();
        category.setName(categoryDto.getName());
        return categorySoftSkillRepo.save(category);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        SoftSkillCategory category = categorySoftSkillRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
        categorySoftSkillRepo.delete(category);
    }

    @Override
    public SoftSkill addSoftSkill(SoftSkillDto softSkillDto) {
        SoftSkill softSkill = new SoftSkill();
        softSkill.setName(softSkillDto.getName());

        SoftSkillCategory category = categorySoftSkillRepo.findById(softSkillDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + softSkillDto.getCategoryId()));
        softSkill.setCategory(category);

        return softSkillRepo.save(softSkill);
    }

    @Override
    public void deleteSoftSkill(Integer softSkillId) {
        SoftSkill softSkill = softSkillRepo.findById(softSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("SoftSkill not found with id " + softSkillId));
        softSkillRepo.delete(softSkill);
    }

}

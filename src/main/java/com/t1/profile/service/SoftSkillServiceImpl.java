package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryDto;
import com.t1.profile.dto.SoftSkillDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.SoftSkillCategoryMapper;
import com.t1.profile.mapper.SoftSkillMapper;
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

    @Autowired
    private SoftSkillCategoryMapper softSkillCategoryMapper;

    @Autowired
    private SoftSkillMapper softSkillMapper;

    @Override
    public SoftSkillCategoryDto addCategory(SoftSkillCategoryDto categoryDto) {
        SoftSkillCategory category = new SoftSkillCategory();
        category.setName(categoryDto.getName());
        SoftSkillCategory savedCategorySoftSkill = categorySoftSkillRepo.save(category);
        return softSkillCategoryMapper.toDto(savedCategorySoftSkill);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        SoftSkillCategory category = categorySoftSkillRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
        categorySoftSkillRepo.delete(category);
    }

    @Override
    public SoftSkillDto addSoftSkill(SoftSkillDto softSkillDto) {
        SoftSkill softSkill = new SoftSkill();
        softSkill.setName(softSkillDto.getName());

        SoftSkillCategory category = categorySoftSkillRepo.findById(softSkillDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + softSkillDto.getCategoryId()));
        softSkill.setCategory(category);
        SoftSkill savedSoftSkill = softSkillRepo.save(softSkill);
        return softSkillMapper.toDto(savedSoftSkill);
    }

    @Override
    public void deleteSoftSkill(Integer softSkillId) {
        SoftSkill softSkill = softSkillRepo.findById(softSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("SoftSkill not found with id " + softSkillId));
        softSkillRepo.delete(softSkill);
    }

}

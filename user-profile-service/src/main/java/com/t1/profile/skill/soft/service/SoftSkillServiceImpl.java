package com.t1.profile.skill.soft.service;

import com.t1.profile.skill.soft.dto.SoftSkillCategoryDto;
import com.t1.profile.skill.soft.dto.SoftSkillCategoryWithSkillsDto;
import com.t1.profile.skill.soft.dto.SoftSkillDto;
import com.t1.profile.skill.soft.exception.CategorySoftSkillNotFoundException;
import com.t1.profile.skill.soft.exception.SoftSkillNotFoundException;
import com.t1.profile.skill.soft.mapper.SoftSkillCategoryMapper;
import com.t1.profile.skill.soft.mapper.SoftSkillMapper;
import com.t1.profile.skill.soft.model.SoftSkill;
import com.t1.profile.skill.soft.model.SoftSkillCategory;
import com.t1.profile.skill.soft.repository.CategorySoftSkillRepo;
import com.t1.profile.skill.soft.repository.SoftSkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<SoftSkillCategoryDto> getAllSoftSkillCategory() {
        return softSkillCategoryMapper.toDtoList(categorySoftSkillRepo.findAll());
    }

    @Override
    public SoftSkillCategoryDto addCategory(SoftSkillCategoryDto categoryDto) {
        SoftSkillCategory category = new SoftSkillCategory();
        category.setName(categoryDto.getName());
        SoftSkillCategory savedCategorySoftSkill = categorySoftSkillRepo.save(category);
        return softSkillCategoryMapper.toDto(savedCategorySoftSkill);
    }

    @Override
    public List<SoftSkillDto> getSoftSkillsByCategory(Integer categoryId) {
        SoftSkillCategory category = categorySoftSkillRepo.findById(categoryId)
                .orElseThrow(() -> new CategorySoftSkillNotFoundException(categoryId));

        List<SoftSkill> softSkills = softSkillRepo.findByCategory(category);
        return softSkills.stream()
                .map(softSkillMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SoftSkillCategoryWithSkillsDto> getAllSoftSkillCategoriesWithSkills() {
        List<SoftSkillCategory> categories = categorySoftSkillRepo.findAll();
        return categories.stream()
                .map(category -> {
                    SoftSkillCategoryWithSkillsDto dto = new SoftSkillCategoryWithSkillsDto();
                    dto.setId(category.getId());
                    dto.setName(category.getName());

                    List<SoftSkillDto> skillDtos = category.getSoftSkills().stream()
                            .map(softSkillMapper::toDto)
                            .collect(Collectors.toList());
                    dto.setSoftSkills(skillDtos);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        SoftSkillCategory category = categorySoftSkillRepo.findById(categoryId)
                .orElseThrow(() -> new CategorySoftSkillNotFoundException(categoryId));
        categorySoftSkillRepo.delete(category);
    }

    @Override
    public SoftSkillDto addSoftSkill(Integer categoryId, SoftSkillDto softSkillDto) {
        SoftSkill softSkill = new SoftSkill();
        softSkill.setName(softSkillDto.getName());

        SoftSkillCategory category = categorySoftSkillRepo.findById(categoryId)
                .orElseThrow(() -> new CategorySoftSkillNotFoundException(categoryId));
        softSkill.setCategory(category);
        SoftSkill savedSoftSkill = softSkillRepo.save(softSkill);
        return softSkillMapper.toDto(savedSoftSkill);
    }

    @Override
    public void deleteSoftSkill(Integer softSkillId) {
        SoftSkill softSkill = softSkillRepo.findById(softSkillId)
                .orElseThrow(() -> new SoftSkillNotFoundException(softSkillId));
        softSkillRepo.delete(softSkill);
    }

}

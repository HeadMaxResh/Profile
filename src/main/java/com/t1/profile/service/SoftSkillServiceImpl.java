package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryDto;
import com.t1.profile.dto.SoftSkillCategoryWithSkillsDto;
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
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));

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
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
        categorySoftSkillRepo.delete(category);
    }

    @Override
    public SoftSkillDto addSoftSkill(Integer categoryId, SoftSkillDto softSkillDto) {
        SoftSkill softSkill = new SoftSkill();
        softSkill.setName(softSkillDto.getName());

        SoftSkillCategory category = categorySoftSkillRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id " + categoryId /*softSkillDto.getCategory().getId()*/)
                );
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

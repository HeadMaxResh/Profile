package com.t1.profile.controller;

import com.t1.profile.dto.SoftSkillCategoryDto;
import com.t1.profile.dto.SoftSkillDto;
import com.t1.profile.exception.ResourceNotFoundException;
import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.SoftSkillCategory;
import com.t1.profile.repository.SoftSkillCategoryRepo;
import com.t1.profile.repository.SoftSkillRepo;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/soft-skill")
public class SoftSkillController {

    @Autowired
    private SoftSkillRepo softSkillRepo;

    @Autowired
    private SoftSkillCategoryRepo softSkillCategoryRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/category/add")
    public ResponseEntity<SoftSkillCategory> addCategory(@RequestBody SoftSkillCategoryDto categoryDto) {

        SoftSkillCategory category = new SoftSkillCategory();
        category.setName(categoryDto.getName());

        SoftSkillCategory savedCategory = softSkillCategoryRepo.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @DeleteMapping("/category/{categoryId}/delete")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        SoftSkillCategory category = softSkillCategoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));

        softSkillCategoryRepo.delete(category);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    public ResponseEntity<SoftSkill> addSoftSkill(@RequestBody SoftSkillDto softSkillDto) {
        SoftSkill softSkill = new SoftSkill();
        softSkill.setName(softSkillDto.getName());

        SoftSkillCategory category = softSkillCategoryRepo.findById(softSkillDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + softSkillDto.getCategoryId()));
        softSkill.setCategory(category);

        SoftSkill savedSoftSkill = softSkillRepo.save(softSkill);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSoftSkill);
    }

    @DeleteMapping("/{softSkillId}/delete")
    public ResponseEntity<Void> deleteSoftSkill(@PathVariable Integer softSkillId) {
        SoftSkill softSkill = softSkillRepo.findById(softSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("SoftSkill not found with id " + softSkillId));

        softSkillRepo.delete(softSkill);
        return ResponseEntity.noContent().build();
    }

}

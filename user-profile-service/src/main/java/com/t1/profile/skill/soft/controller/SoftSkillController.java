package com.t1.profile.skill.soft.controller;

import com.t1.profile.skill.soft.dto.SoftSkillCategoryDto;
import com.t1.profile.skill.soft.dto.SoftSkillCategoryWithSkillsDto;
import com.t1.profile.skill.soft.dto.SoftSkillDto;
import com.t1.profile.skill.soft.service.SoftSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soft-skills")
public class SoftSkillController {

    @Autowired
    private SoftSkillService softSkillService;

    @GetMapping("/category/all")
    public List<SoftSkillCategoryDto> getAllSoftSkillCategories() {
        return softSkillService.getAllSoftSkillCategory();
    }

    @GetMapping("/category/{categoryId}")
    public List<SoftSkillDto> getSoftSkillsByCategory(@PathVariable Integer categoryId) {
        return softSkillService.getSoftSkillsByCategory(categoryId);
    }

    @PostMapping("/category/add")
    public ResponseEntity<SoftSkillCategoryDto> addCategory(@RequestBody SoftSkillCategoryDto categoryDto) {
        SoftSkillCategoryDto savedCategory = softSkillService.addCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @DeleteMapping("/category/{categoryId}/delete")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        softSkillService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/category/{categoryId}/add-soft-skill")
    public ResponseEntity<SoftSkillDto> addSoftSkill(@PathVariable Integer categoryId, @RequestBody SoftSkillDto softSkillDto) {
        SoftSkillDto savedSoftSkill = softSkillService.addSoftSkill(categoryId, softSkillDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSoftSkill);
    }

    @GetMapping("/categories-with-skills")
    public List<SoftSkillCategoryWithSkillsDto> getAllSoftSkillCategoriesWithSkills() {
        return softSkillService.getAllSoftSkillCategoriesWithSkills();
    }

    @DeleteMapping("/{softSkillId}/delete")
    public ResponseEntity<Void> deleteSoftSkill(@PathVariable Integer softSkillId) {
        softSkillService.deleteSoftSkill(softSkillId);
        return ResponseEntity.noContent().build();
    }

}

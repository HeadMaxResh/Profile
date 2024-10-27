package com.t1.profile.controller;

import com.t1.profile.dto.SoftSkillCategoryDto;
import com.t1.profile.dto.SoftSkillDto;
import com.t1.profile.service.SoftSkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/soft-skill")
public class SoftSkillController {

    @Autowired
    private SoftSkillServiceImpl softSkillService;

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

    @PostMapping("/add")
    public ResponseEntity<SoftSkillDto> addSoftSkill(@RequestBody SoftSkillDto softSkillDto) {
        SoftSkillDto savedSoftSkill = softSkillService.addSoftSkill(softSkillDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSoftSkill);
    }

    @DeleteMapping("/{softSkillId}/delete")
    public ResponseEntity<Void> deleteSoftSkill(@PathVariable Integer softSkillId) {
        softSkillService.deleteSoftSkill(softSkillId);
        return ResponseEntity.noContent().build();
    }

}

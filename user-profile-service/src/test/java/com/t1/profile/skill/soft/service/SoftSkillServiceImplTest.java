package com.t1.profile.skill.soft.service;

import com.t1.profile.skill.soft.dto.SoftSkillCategoryDto;
import com.t1.profile.skill.soft.dto.SoftSkillDto;
import com.t1.profile.skill.soft.exception.CategorySoftSkillNotFoundException;
import com.t1.profile.skill.soft.exception.SoftSkillNotFoundException;
import com.t1.profile.skill.soft.mapper.SoftSkillCategoryMapper;
import com.t1.profile.skill.soft.mapper.SoftSkillMapper;
import com.t1.profile.skill.soft.model.SoftSkill;
import com.t1.profile.skill.soft.model.SoftSkillCategory;
import com.t1.profile.skill.soft.repository.CategorySoftSkillRepo;
import com.t1.profile.skill.soft.repository.SoftSkillRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SoftSkillServiceImplTest {

    @InjectMocks
    private SoftSkillServiceImpl softSkillService;

    @Mock
    private SoftSkillRepo softSkillRepo;

    @Mock
    private CategorySoftSkillRepo categorySoftSkillRepo;

    @Mock
    private SoftSkillCategoryMapper softSkillCategoryMapper;

    @Mock
    private SoftSkillMapper softSkillMapper;

    private SoftSkill softSkill;
    private SoftSkillDto softSkillDto;
    private SoftSkillCategory category;
    private SoftSkillCategoryDto categoryDto;
    Integer categoryId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new SoftSkillCategory();
        category.setId(1);
        category.setName("Communication");

        categoryDto = new SoftSkillCategoryDto();
        categoryId = 1;
        categoryDto.setId(categoryId);
        categoryDto.setName("Communication");

        softSkill = new SoftSkill();
        softSkill.setId(1);
        softSkill.setName("Listening skills");
        softSkill.setCategory(category);

        softSkillDto = new SoftSkillDto();
        softSkillDto.setId(1);
        softSkillDto.setName("Listening skills");
        //softSkillDto.setCategory(categoryDto);
    }

    @Test
    public void addCategory_shouldReturnSoftSkillCategoryDto() {
        when(softSkillCategoryMapper.toDto(any(SoftSkillCategory.class))).thenReturn(categoryDto);
        when(categorySoftSkillRepo.save(any(SoftSkillCategory.class))).thenReturn(category);

        SoftSkillCategoryDto result = softSkillService.addCategory(categoryDto);

        assertEquals(categoryDto.getName(), result.getName());
        verify(categorySoftSkillRepo, times(1)).save(any(SoftSkillCategory.class));
    }

    @Test
    public void deleteCategory_shouldDeleteCategory() {
        when(categorySoftSkillRepo.findById(1)).thenReturn(Optional.of(category));

        softSkillService.deleteCategory(1);

        verify(categorySoftSkillRepo, times(1)).delete(category);
    }

    @Test
    public void deleteCategory_shouldThrowResourceNotFoundException() {
        when(categorySoftSkillRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CategorySoftSkillNotFoundException.class, ()
                -> softSkillService.deleteCategory(1));

        assertEquals(CategorySoftSkillNotFoundException.getMessage(1), exception.getMessage());
    }

    @Test
    public void addSoftSkill_shouldReturnSoftSkillDto() {
        when(categorySoftSkillRepo.findById(1)).thenReturn(Optional.of(category));
        when(softSkillMapper.toDto(any(SoftSkill.class))).thenReturn(softSkillDto);
        when(softSkillRepo.save(any(SoftSkill.class))).thenReturn(softSkill);

        SoftSkillDto result = softSkillService.addSoftSkill(categoryId, softSkillDto);

        assertEquals(softSkillDto.getName(), result.getName());
        verify(softSkillRepo, times(1)).save(any(SoftSkill.class));
    }

    @Test
    public void addSoftSkill_shouldThrowResourceNotFoundException_whenCategoryNotFound() {
        when(categorySoftSkillRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CategorySoftSkillNotFoundException.class, ()
                -> softSkillService.addSoftSkill(categoryId, softSkillDto));

        assertEquals(CategorySoftSkillNotFoundException.getMessage(1), exception.getMessage());
    }

    @Test
    public void deleteSoftSkill_shouldDeleteSoftSkill() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.of(softSkill));

        softSkillService.deleteSoftSkill(1);

        verify(softSkillRepo, times(1)).delete(softSkill);
    }

    @Test
    public void deleteSoftSkill_shouldThrowResourceNotFoundException() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SoftSkillNotFoundException.class, ()
                -> softSkillService.deleteSoftSkill(1));

        assertEquals(SoftSkillNotFoundException.getMessage(1), exception.getMessage());
    }

}

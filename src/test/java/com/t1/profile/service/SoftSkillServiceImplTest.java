package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryDto;
import com.t1.profile.dto.SoftSkillDto;
import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.SoftSkillCategory;
import com.t1.profile.repository.CategorySoftSkillRepo;
import com.t1.profile.repository.SoftSkillRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SoftSkillServiceImplTest {

    @InjectMocks
    private SoftSkillServiceImpl softSkillService;

    @Mock
    private SoftSkillRepo softSkillRepo;

    @Mock
    private CategorySoftSkillRepo categorySoftSkillRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddCategory() {
        SoftSkillCategoryDto categoryDto = new SoftSkillCategoryDto();
        categoryDto.setName("Communication");

        SoftSkillCategory category = new SoftSkillCategory();
        category.setId(1);
        category.setName("Communication");

        when(categorySoftSkillRepo.save(any(SoftSkillCategory.class))).thenReturn(category);

        SoftSkillCategory createdCategory = softSkillService.addCategory(categoryDto);

        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory.getId()).isEqualTo(1);
        assertThat(createdCategory.getName()).isEqualTo("Communication");
        verify(categorySoftSkillRepo, times(1)).save(any(SoftSkillCategory.class));
    }

    @Test
    public void testDeleteCategory() {
        Integer categoryId = 1;

        SoftSkillCategory category = new SoftSkillCategory();
        category.setId(categoryId);
        category.setName("Communication");

        when(categorySoftSkillRepo.findById(categoryId)).thenReturn(Optional.of(category));

        softSkillService.deleteCategory(categoryId);

        verify(categorySoftSkillRepo, times(1)).findById(categoryId);
        verify(categorySoftSkillRepo, times(1)).delete(category);
    }

    @Test
    public void testAddSoftSkill() {
        SoftSkillDto softSkillDto = new SoftSkillDto();
        softSkillDto.setName("Teamwork");
        softSkillDto.setCategoryId(1);

        SoftSkillCategory category = new SoftSkillCategory();
        category.setId(1);
        category.setName("Collaboration");

        when(categorySoftSkillRepo.findById(1)).thenReturn(Optional.of(category));

        SoftSkill softSkill = new SoftSkill();
        softSkill.setId(1);
        softSkill.setName("Teamwork");
        softSkill.setCategory(category);

        when(softSkillRepo.save(any(SoftSkill.class))).thenReturn(softSkill);

        SoftSkill createdSoftSkill = softSkillService.addSoftSkill(softSkillDto);

        assertThat(createdSoftSkill).isNotNull();
        assertThat(createdSoftSkill.getId()).isEqualTo(1);
        assertThat(createdSoftSkill.getName()).isEqualTo("Teamwork");
        assertThat(createdSoftSkill.getCategory()).isEqualTo(category);
        verify(categorySoftSkillRepo, times(1)).findById(1);
        verify(softSkillRepo, times(1)).save(any(SoftSkill.class));
    }

    @Test
    public void testDeleteSoftSkill() {
        Integer softSkillId = 1;

        SoftSkill softSkill = new SoftSkill();
        softSkill.setId(softSkillId);
        softSkill.setName("Teamwork");

        when(softSkillRepo.findById(softSkillId)).thenReturn(Optional.of(softSkill));

        softSkillService.deleteSoftSkill(softSkillId);

        verify(softSkillRepo, times(1)).findById(softSkillId);
        verify(softSkillRepo, times(1)).delete(softSkill);
    }

}

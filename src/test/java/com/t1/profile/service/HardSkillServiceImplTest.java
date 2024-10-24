package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.HardSkill;
import com.t1.profile.repository.HardSkillRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HardSkillServiceImplTest {

    @InjectMocks
    private HardSkillServiceImpl hardSkillService;

    @Mock
    private HardSkillRepo hardSkillRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddHardSkill() {
        HardSkillDto hardSkillDto = new HardSkillDto();
        hardSkillDto.setName("Java");

        HardSkill hardSkill = new HardSkill();
        hardSkill.setId(1);
        hardSkill.setName("Java");

        when(hardSkillRepo.save(any(HardSkill.class))).thenReturn(hardSkill);

        HardSkill createdHardSkill = hardSkillService.addHardSkill(hardSkillDto);

        assertThat(createdHardSkill).isNotNull();
        assertThat(createdHardSkill.getId()).isEqualTo(1);
        assertThat(createdHardSkill.getName()).isEqualTo("Java");
        verify(hardSkillRepo, times(1)).save(any(HardSkill.class));
    }

    @Test
    public void testUpdateHardSkill_Success() {
        Integer hardSkillId = 1;
        HardSkillDto hardSkillDto = new HardSkillDto();
        hardSkillDto.setName("Python");

        HardSkill existingHardSkill = new HardSkill();
        existingHardSkill.setId(hardSkillId);
        existingHardSkill.setName("Java");

        when(hardSkillRepo.findById(hardSkillId)).thenReturn(Optional.of(existingHardSkill));
        when(hardSkillRepo.save(any(HardSkill.class))).thenReturn(existingHardSkill);

        HardSkill updatedHardSkill = hardSkillService.updateHardSkill(hardSkillId, hardSkillDto);

        assertThat(updatedHardSkill).isNotNull();
        assertThat(updatedHardSkill.getId()).isEqualTo(hardSkillId);
        assertThat(updatedHardSkill.getName()).isEqualTo("Python");
        verify(hardSkillRepo, times(1)).findById(hardSkillId);
        verify(hardSkillRepo, times(1)).save(existingHardSkill);
    }

    @Test
    public void testUpdateHardSkill_NotFound() {
        Integer hardSkillId = 1;
        HardSkillDto hardSkillDto = new HardSkillDto();
        hardSkillDto.setName("Python");

        when(hardSkillRepo.findById(hardSkillId)).thenReturn(Optional.empty());

        try {
            hardSkillService.updateHardSkill(hardSkillId, hardSkillDto);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("HardSkill not found with id " + hardSkillId);
        }

        verify(hardSkillRepo, times(1)).findById(hardSkillId);
        verify(hardSkillRepo, never()).save(any(HardSkill.class));
    }

    @Test
    public void testDeleteHardSkill_Success() {
        Integer hardSkillId = 1;

        HardSkill existingHardSkill = new HardSkill();
        existingHardSkill.setId(hardSkillId);
        existingHardSkill.setName("Java");

        when(hardSkillRepo.findById(hardSkillId)).thenReturn(Optional.of(existingHardSkill));

        hardSkillService.deleteHardSkill(hardSkillId);

        verify(hardSkillRepo, times(1)).findById(hardSkillId);
        verify(hardSkillRepo, times(1)).delete(existingHardSkill);
    }

    @Test
    public void testDeleteHardSkill_NotFound() {
        Integer hardSkillId = 1;

        when(hardSkillRepo.findById(hardSkillId)).thenReturn(Optional.empty());

        try {
            hardSkillService.deleteHardSkill(hardSkillId);
        } catch (ResourceNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("HardSkill not found with id " + hardSkillId);
        }

        verify(hardSkillRepo, times(1)).findById(hardSkillId);
        verify(hardSkillRepo, never()).delete(any(HardSkill.class));
    }

}

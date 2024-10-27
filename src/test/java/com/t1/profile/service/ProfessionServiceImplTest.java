package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.dto.ProfessionDto;
import com.t1.profile.mapper.HardSkillMapper;
import com.t1.profile.mapper.ProfessionMapper;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.repository.HardSkillRepo;
import com.t1.profile.repository.ProfessionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProfessionServiceImplTest {

    @InjectMocks
    private ProfessionServiceImpl professionService;

    @Mock
    private ProfessionRepo professionRepo;

    @Mock
    private HardSkillRepo hardSkillRepo;

    @Mock
    private ProfessionMapper professionMapper;

    @Mock
    private HardSkillMapper hardSkillMapper;

    private Profession profession;
    private ProfessionDto professionDto;
    private HardSkill hardSkill;
    private HardSkillDto hardSkillDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        profession = new Profession();
        profession.setId(1);
        profession.setName("Software Developer");

        professionDto = new ProfessionDto();
        professionDto.setId(1);
        professionDto.setName("Software Developer");

        hardSkill = new HardSkill();
        hardSkill.setId(1);
        hardSkill.setName("Java");

        hardSkillDto = new HardSkillDto();
        hardSkillDto.setId(1);
        hardSkillDto.setName("Java");
    }

    @Test
    public void addProfession_shouldReturnProfessionDto() {
        when(professionMapper.toEntity(any(ProfessionDto.class))).thenReturn(profession);
        when(professionRepo.save(any(Profession.class))).thenReturn(profession);
        when(professionMapper.toDto(any(Profession.class))).thenReturn(professionDto);

        ProfessionDto result = professionService.addProfession(professionDto);

        assertEquals(professionDto.getName(), result.getName());
        verify(professionRepo, times(1)).save(any(Profession.class));
    }

    @Test
    public void addHardSkillToProfession_shouldReturnHardSkillDto() {
        when(professionRepo.findById(1)).thenReturn(Optional.of(profession));
        when(hardSkillMapper.toEntity(any(HardSkillDto.class))).thenReturn(hardSkill);
        when(hardSkillRepo.save(any(HardSkill.class))).thenReturn(hardSkill);
        when(hardSkillMapper.toDto(any(HardSkill.class))).thenReturn(hardSkillDto);

        HardSkillDto result = professionService.addHardSkillToProfession(1, hardSkillDto);

        assertEquals(hardSkillDto.getName(), result.getName());
        verify(professionRepo, times(1)).findById(1);
        verify(hardSkillRepo, times(1)).save(any(HardSkill.class));
    }

    @Test
    public void addExistingHardSkillToProfession_shouldReturnHardSkillDto() {
        when(professionRepo.findById(1)).thenReturn(Optional.of(profession));
        when(hardSkillRepo.findById(1)).thenReturn(Optional.of(hardSkill));
        when(hardSkillMapper.toDto(any(HardSkill.class))).thenReturn(hardSkillDto);

        HardSkillDto result = professionService.addExistingHardSkillToProfession(1, 1);

        assertEquals(hardSkillDto.getName(), result.getName());
        verify(professionRepo, times(1)).findById(1);
        verify(hardSkillRepo, times(1)).findById(1);
    }

    @Test
    public void removeHardSkillFromProfession_shouldRemoveHardSkill() {
        when(professionRepo.findById(1)).thenReturn(Optional.of(profession));
        when(hardSkillRepo.findById(1)).thenReturn(Optional.of(hardSkill));

        professionService.removeHardSkillFromProfession(1, 1);

        assertFalse(profession.getMainHardSkills().contains(hardSkill));
        verify(professionRepo, times(1)).save(any(Profession.class));
    }

    @Test
    public void deleteProfession_shouldDeleteProfession() {
        when(professionRepo.findById(1)).thenReturn(Optional.of(profession));

        professionService.deleteProfession(1);

        verify(professionRepo, times(1)).delete(profession);
    }

    @Test
    public void getHardSkillsByProfession_shouldReturnSetOfHardSkillDto() {
        profession.getMainHardSkills().add(hardSkill);
        when(professionRepo.findById(1)).thenReturn(Optional.of(profession));
        when(hardSkillMapper.toDto(any(HardSkill.class))).thenReturn(hardSkillDto);

        Set<HardSkillDto> result = professionService.getHardSkillsByProfession(1);

        assertEquals(1, result.size());
        assertTrue(result.contains(hardSkillDto));
    }
}

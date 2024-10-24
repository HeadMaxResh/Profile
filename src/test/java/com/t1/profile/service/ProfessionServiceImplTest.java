package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.dto.ProfessionDto;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.repository.HardSkillRepo;
import com.t1.profile.repository.ProfessionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProfessionServiceImplTest {

    @InjectMocks
    private ProfessionServiceImpl professionService;

    @Mock
    private ProfessionRepo professionRepo;

    @Mock
    private HardSkillRepo hardSkillRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProfession() {
        ProfessionDto professionDto = new ProfessionDto();
        professionDto.setName("Software Developer");

        Profession profession = new Profession();
        profession.setId(1);
        profession.setName("Software Developer");

        when(professionRepo.save(any(Profession.class))).thenReturn(profession);

        Profession createdProfession = professionService.addProfession(professionDto);

        assertThat(createdProfession).isNotNull();
        assertThat(createdProfession.getId()).isEqualTo(1);
        assertThat(createdProfession.getName()).isEqualTo("Software Developer");
        verify(professionRepo, times(1)).save(any(Profession.class));
    }

    @Test
    public void testAddHardSkillToProfession() {
        Integer professionId = 1;
        HardSkillDto hardSkillDto = new HardSkillDto();
        hardSkillDto.setName("Java");

        Profession profession = new Profession();
        profession.setId(professionId);
        profession.setName("Software Developer");
        profession.setMainHardSkills(new HashSet<>());

        when(professionRepo.findById(professionId)).thenReturn(Optional.of(profession));

        HardSkill hardSkill = new HardSkill();
        hardSkill.setId(1);
        hardSkill.setName("Java");

        when(hardSkillRepo.save(any(HardSkill.class))).thenReturn(hardSkill);

        HardSkill createdHardSkill = professionService.addHardSkillToProfession(professionId, hardSkillDto);

        assertThat(createdHardSkill).isNotNull();
        assertThat(createdHardSkill.getId()).isEqualTo(1);
        assertThat(profession.getMainHardSkills()).hasSize(1);
        verify(professionRepo, times(1)).findById(professionId);
        verify(hardSkillRepo, times(1)).save(any(HardSkill.class));
    }

    @Test
    public void testAddExistingHardSkillToProfession() {
        Integer professionId = 1;
        Integer hardSkillId = 1;

        Profession profession = new Profession();
        profession.setId(professionId);
        profession.setMainHardSkills(new HashSet<>());

        HardSkill hardSkill = new HardSkill();
        hardSkill.setId(hardSkillId);
        hardSkill.setName("Java");

        when(professionRepo.findById(professionId)).thenReturn(Optional.of(profession));
        when(hardSkillRepo.findById(hardSkillId)).thenReturn(Optional.of(hardSkill));

        HardSkill addedHardSkill = professionService.addExistingHardSkillToProfession(professionId, hardSkillId);

        assertThat(addedHardSkill).isNotNull();
        assertThat(addedHardSkill.getId()).isEqualTo(hardSkillId);
        assertThat(profession.getMainHardSkills()).contains(addedHardSkill);
        verify(professionRepo, times(1)).findById(professionId);
        verify(hardSkillRepo, times(1)).findById(hardSkillId);
        verify(professionRepo, times(1)).save(profession);
    }

    @Test
    public void testRemoveHardSkillFromProfession() {
        Integer professionId = 1;
        Integer hardSkillId = 1;

        Profession profession = new Profession();
        profession.setId(professionId);
        HardSkill hardSkill = new HardSkill();
        hardSkill.setId(hardSkillId);
        profession.setMainHardSkills(new HashSet<>(Set.of(hardSkill)));

        when(professionRepo.findById(professionId)).thenReturn(Optional.of(profession));
        when(hardSkillRepo.findById(hardSkillId)).thenReturn(Optional.of(hardSkill));

        professionService.removeHardSkillFromProfession(professionId, hardSkillId);

        assertThat(profession.getMainHardSkills()).doesNotContain(hardSkill);
        verify(professionRepo, times(1)).findById(professionId);
        verify(hardSkillRepo, times(1)).findById(hardSkillId);
        verify(professionRepo, times(1)).save(profession);
    }

    @Test
    public void testDeleteProfession() {
        Integer professionId = 1;

        Profession profession = new Profession();
        profession.setId(professionId);
        profession.setName("Software Developer");

        when(professionRepo.findById(professionId)).thenReturn(Optional.of(profession));

        professionService.deleteProfession(professionId);

        verify(professionRepo, times(1)).findById(professionId);
        verify(professionRepo, times(1)).delete(profession);
    }

    @Test
    public void testGetHardSkillsByProfession() {
        Integer professionId = 1;

        Profession profession = new Profession();
        profession.setId(professionId);
        HardSkill hardSkill = new HardSkill();
        hardSkill.setId(1);
        hardSkill.setName("Java");
        profession.setMainHardSkills(new HashSet<>(Set.of(hardSkill)));

        when(professionRepo.findById(professionId)).thenReturn(Optional.of(profession));

        Set<HardSkill> hardSkills = professionService.getHardSkillsByProfession(professionId);

        assertThat(hardSkills).isNotNull();
        assertThat(hardSkills).contains(hardSkill);
        verify(professionRepo, times(1)).findById(professionId);
    }
}

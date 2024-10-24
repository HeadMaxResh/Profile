package com.t1.profile.service;

import com.t1.profile.dto.UserDto;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.User;
import com.t1.profile.repository.HardSkillRepo;
import com.t1.profile.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserHardSkillServiceImplTest {

    @InjectMocks
    private UserHardSkillServiceImpl userHardSkillService;

    @Mock
    private HardSkillRepo hardSkillRepo;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetHardSkillsByUser() {
        Integer userId = 1;

        User user = new User();
        user.setId(userId);
        Set<HardSkill> hardSkills = new HashSet<>();
        user.setHardSkills(hardSkills);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        Set<HardSkill> retrievedHardSkills = userHardSkillService.getHardSkillsByUser(userId);

        assertThat(retrievedHardSkills).isEqualTo(hardSkills);
        verify(userRepo, times(1)).findById(userId);
    }

    @Test
    public void testAddHardSkillToUser() {
        Integer userId = 1;
        Integer hardSkillId = 2;

        User user = new User();
        user.setId(userId);
        Set<HardSkill> hardSkills = new HashSet<>();
        user.setHardSkills(hardSkills);

        HardSkill hardSkill = new HardSkill();
        hardSkill.setId(hardSkillId);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(hardSkillId)).thenReturn(Optional.of(hardSkill));
        when(userRepo.save(any(User.class))).thenReturn(user);

        UserDto updatedUserDto = userHardSkillService.addHardSkillToUser(userId, hardSkillId);

        assertThat(updatedUserDto).isNotNull();
        assertThat(updatedUserDto.getHardSkills()).contains(hardSkill);
        verify(userRepo, times(1)).findById(userId);
        verify(hardSkillRepo, times(1)).findById(hardSkillId);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void testRemoveHardSkillFromUser() {
        Integer userId = 1;
        Integer hardSkillId = 2;

        User user = new User();
        user.setId(userId);
        HardSkill hardSkill = new HardSkill();
        hardSkill.setId(hardSkillId);
        Set<HardSkill> hardSkills = new HashSet<>(Collections.singleton(hardSkill));
        user.setHardSkills(hardSkills);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(hardSkillId)).thenReturn(Optional.of(hardSkill));
        when(userRepo.save(any(User.class))).thenReturn(user);

        userHardSkillService.removeHardSkillFromUser(userId, hardSkillId);

        assertThat(user.getHardSkills()).doesNotContain(hardSkill);
        verify(userRepo, times(1)).findById(userId);
        verify(hardSkillRepo, times(1)).findById(hardSkillId);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void testRemoveHardSkillFromUser_HardSkillNotAssociated() {
        Integer userId = 1;
        Integer hardSkillId = 2;

        User user = new User();
        user.setId(userId);
        Set<HardSkill> hardSkills = new HashSet<>();
        user.setHardSkills(hardSkills); // Пользователь не имеет хардскиллов

        HardSkill hardSkill = new HardSkill();
        hardSkill.setId(hardSkillId);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(hardSkillId)).thenReturn(Optional.of(hardSkill));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userHardSkillService.removeHardSkillFromUser(userId, hardSkillId);
        });

        assertThat(exception.getMessage()).isEqualTo("Хардскилл не ассоциирован с пользователем.");
        verify(userRepo, times(1)).findById(userId);
        verify(hardSkillRepo, times(1)).findById(hardSkillId);
        verify(userRepo, never()).save(any(User.class));
    }
}

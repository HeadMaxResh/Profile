package com.t1.profile.service;

import com.t1.profile.dto.UserDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.Profession;
import com.t1.profile.model.User;
import com.t1.profile.repository.ProfessionRepo;
import com.t1.profile.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserProfessionImplTest {

    @InjectMocks
    private UserProfessionServiceImpl userProfessionService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ProfessionRepo professionRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProfessionToUser() {
        Integer userId = 1;
        Integer professionId = 2;

        User user = new User();
        user.setId(userId);

        Profession profession = new Profession();
        profession.setId(professionId);
        profession.setName("Software Developer");

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(professionRepo.findById(professionId)).thenReturn(Optional.of(profession));
        when(userRepo.save(any(User.class))).thenReturn(user);

        UserDto updatedUserDto = userProfessionService.addProfessionToUser(userId, professionId);

        assertThat(updatedUserDto).isNotNull();
        assertThat(updatedUserDto.getProfession()).isEqualTo(profession);
        verify(userRepo, times(1)).findById(userId);
        verify(professionRepo, times(1)).findById(professionId);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void testUpdateProfessionForUser() {
        Integer userId = 1;
        Integer professionId = 2;

        User user = new User();
        user.setId(userId);

        Profession profession = new Profession();
        profession.setId(professionId);
        profession.setName("Software Developer");

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(professionRepo.findById(professionId)).thenReturn(Optional.of(profession));
        when(userRepo.save(any(User.class))).thenReturn(user);

        UserDto updatedUserDto = userProfessionService.updateProfessionForUser(userId, professionId);

        assertThat(updatedUserDto).isNotNull();
        assertThat(updatedUserDto.getProfession()).isEqualTo(profession);
        verify(userRepo, times(1)).findById(userId);
        verify(professionRepo, times(1)).findById(professionId);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void testAddProfessionToUser_UserNotFound() {
        Integer userId = 1;
        Integer professionId = 2;

        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userProfessionService.addProfessionToUser(userId, professionId);
        });

        assertThat(exception.getMessage()).isEqualTo("User not found with id " + userId);
        verify(userRepo, times(1)).findById(userId);
        verify(professionRepo, never()).findById(anyInt());
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    public void testUpdateProfessionForUser_UserNotFound() {
        Integer userId = 1;
        Integer professionId = 2;

        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userProfessionService.updateProfessionForUser(userId, professionId);
        });

        assertThat(exception.getMessage()).isEqualTo("User not found with id " + userId);
        verify(userRepo, times(1)).findById(userId);
        verify(professionRepo, never()).findById(anyInt());
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    public void testAddProfessionToUser_ProfessionNotFound() {
        Integer userId = 1;
        Integer professionId = 2;

        User user = new User();
        user.setId(userId);

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(professionRepo.findById(professionId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userProfessionService.addProfessionToUser(userId, professionId);
        });

        assertThat(exception.getMessage()).isEqualTo("Profession not found with id " + professionId);
        verify(userRepo, times(1)).findById(userId);
        verify(professionRepo, times(1)).findById(professionId);
        verify(userRepo, never()).save(any(User.class));
    }

}

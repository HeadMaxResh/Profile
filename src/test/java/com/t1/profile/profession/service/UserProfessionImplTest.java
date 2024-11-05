package com.t1.profile.profession.service;

import com.t1.profile.profession.exception.ProfessionNotFoundException;
import com.t1.profile.user.dto.UserDto;
import com.t1.profile.profession.mapper.ProfessionMapper;
import com.t1.profile.user.exception.EntityNotFoundByIdException;
import com.t1.profile.user.mapper.UserMapper;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.user.model.User;
import com.t1.profile.profession.repository.ProfessionRepo;
import com.t1.profile.profession.service.UserProfessionServiceImpl;
import com.t1.profile.user.repository.UserRepo;
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

public class UserProfessionImplTest {

    @InjectMocks
    private UserProfessionServiceImpl userProfessionService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ProfessionRepo professionRepo;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ProfessionMapper professionMapper;

    private User user;
    private Profession profession;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setFirstName("User 1");

        profession = new Profession();
        profession.setId(1L);
        profession.setName("Developer");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("User 1");
        userDto.setProfession(professionMapper.toDto(profession));
    }

    @Test
    public void addProfessionToUser_shouldReturnUserDto() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(professionRepo.findById(1L)).thenReturn(Optional.of(profession));
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        UserDto result = userProfessionService.addProfessionToUser(1L, 1L);

        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getProfession().getId(), result.getProfession().getId());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void addProfessionToUser_shouldThrowUserNotFoundException_whenUserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundByIdException.class, ()
                -> userProfessionService.addProfessionToUser(1L, 1L));

        assertEquals("User not found with id 1", exception.getMessage());
    }

    @Test
    public void addProfessionToUser_shouldThrowProfessionNotFoundException_whenProfessionNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(professionRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProfessionNotFoundException.class, ()
                -> userProfessionService.addProfessionToUser(1L, 1L));

        assertEquals("Profession not found with id 1", exception.getMessage());
    }

    @Test
    public void updateProfessionForUser_shouldReturnUserDto() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(professionRepo.findById(1L)).thenReturn(Optional.of(profession));
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        UserDto result = userProfessionService.updateProfessionForUser(1L, 1L);

        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getProfession().getId(), result.getProfession().getId());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void updateProfessionForUser_shouldThrowUserNotFoundException_whenUserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundByIdException.class, ()
                -> userProfessionService.updateProfessionForUser(1L, 1L));

        assertEquals("User not found with id 1", exception.getMessage());
    }

    @Test
    public void updateProfessionForUser_shouldThrowProfessionNotFoundException_whenProfessionNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(professionRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProfessionNotFoundException.class, ()
                -> userProfessionService.updateProfessionForUser(1L, 1L));

        assertEquals("Profession not found with id 1", exception.getMessage());
    }

}

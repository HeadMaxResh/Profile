package com.t1.profile.user.service;

import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.mapper.UserMapper;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.UserRepo;
import com.t1.profile.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserMapper userMapper;

    private User user;
    private UserDto userDto;
    private List<User> userList;
    private List<UserDto> userDtoList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFirstName("User 1");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setFirstName("User 1");

        userList = new ArrayList<>();
        userList.add(user);

        userDtoList = new ArrayList<>();
        userDtoList.add(userDto);
    }

    @Test
    public void getAllUsers_shouldReturnListOfUserDto() {
        when(userRepo.findAll()).thenReturn(userList);
        when(userMapper.toDtoList(anyList())).thenReturn(userDtoList);

        List<UserDto> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDto.getEmail(), result.get(0).getEmail());
        verify(userRepo, times(1)).findAll();
        verify(userMapper, times(1)).toDtoList(userList);
    }

    @Test
    public void findByEmail_shouldReturnUserDto_whenUserExists() {
        when(userRepo.findByEmail("test@example.com")).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);

        UserDto result = userService.findByEmail("test@example.com");

        assertNotNull(result);
        assertEquals(userDto.getEmail(), result.getEmail());
        verify(userRepo, times(1)).findByEmail("test@example.com");
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    public void findByEmail_shouldReturnNull_whenUserDoesNotExist() {
        when(userRepo.findByEmail("nonexistent@example.com")).thenReturn(null);

        UserDto result = userService.findByEmail("nonexistent@example.com");

        assertNull(result);
        verify(userRepo, times(1)).findByEmail("nonexistent@example.com");
    }

    @Test
    public void findByProfession_Id_shouldReturnListOfUserDto() {
        Profession profession = new Profession();
        profession.setId(1L);
        profession.setName("Разработчик");

        when(userRepo.findByProfession(any(Profession.class))).thenReturn(userList);
        when(userMapper.toDtoList(anyList())).thenReturn(userDtoList);

        List<UserDto> result = userService.findByProfessionId(profession.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDto.getEmail(), result.get(0).getEmail());
        verify(userRepo, times(1)).findByProfession(profession);
        verify(userMapper, times(1)).toDtoList(userList);
    }
}

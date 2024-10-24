package com.t1.profile.service;

import com.t1.profile.dto.UserDto;
import com.t1.profile.model.Profession;
import com.t1.profile.model.User;
import com.t1.profile.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setDateOfBirth(LocalDate.of(1990, 1, 1));

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setDateOfBirth(LocalDate.of(1995, 5, 15));

        when(userRepo.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDto> userDtos = userService.getAllUsers();

        assertThat(userDtos).isNotNull();
        assertThat(userDtos.size()).isEqualTo(2);
        assertThat(userDtos.get(0).getFirstName()).isEqualTo("John");
        assertThat(userDtos.get(1).getFirstName()).isEqualTo("Jane");

        verify(userRepo, times(1)).findAll();
    }

    @Test
    public void testFindByEmail_UserFound() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Alice");
        user.setLastName("Smith");
        user.setEmail("alice.smith@example.com");

        when(userRepo.findByEmail("alice.smith@example.com")).thenReturn(user);

        UserDto foundUserDto = userService.findByEmail("alice.smith@example.com");

        assertThat(foundUserDto).isNotNull();
        assertThat(foundUserDto.getFirstName()).isEqualTo("Alice");

        verify(userRepo, times(1)).findByEmail("alice.smith@example.com");
    }

    @Test
    public void testFindByEmail_UserNotFound() {
        when(userRepo.findByEmail("unknown@example.com")).thenReturn(null);

        UserDto foundUserDto = userService.findByEmail("unknown@example.com");

        assertThat(foundUserDto).isNull();
        verify(userRepo, times(1)).findByEmail("unknown@example.com");
    }

    @Test
    public void testFindByProfession() {
        Profession profession = new Profession();
        profession.setName("Software Developer");

        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.doe@example.com");
        user1.setProfession(profession);

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setProfession(profession);

        when(userRepo.findByProfession(profession)).thenReturn(Arrays.asList(user1, user2));

        List<UserDto> userDtos = userService.findByProfession(profession);

        assertThat(userDtos).isNotNull();
        assertThat(userDtos.size()).isEqualTo(2);
        assertThat(userDtos).extracting(UserDto::getFirstName).containsExactly("John", "Jane");

        verify(userRepo, times(1)).findByProfession(profession);
    }
}

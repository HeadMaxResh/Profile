package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.dto.UserDto;
import com.t1.profile.dto.UserHardSkillsDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.HardSkillMapper;
import com.t1.profile.mapper.UserMapper;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.model.User;
import com.t1.profile.repository.HardSkillRepo;
import com.t1.profile.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserHardSkillServiceImplTest {

    @InjectMocks
    private UserHardSkillServiceImpl userHardSkillService;

    @Mock
    private HardSkillRepo hardSkillRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private HardSkillMapper hardSkillMapper;

    @Mock
    private UserMapper userMapper;

    private User user;
    private HardSkill hardSkill;
    private HardSkillDto hardSkillDto;
    private UserDto userDto;
    private List<HardSkill> userHardSkills;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1);
        user.setHardSkills(new HashSet<>());
        user.setProfession(new Profession());

        hardSkill = new HardSkill();
        hardSkill.setId(1);
        hardSkill.setName("Java");

        hardSkillDto = new HardSkillDto();
        hardSkillDto.setId(1);
        hardSkillDto.setName("Java");

        userDto = new UserDto();
        userDto.setId(1);
        userDto.setHardSkills(new HashSet<HardSkillDto>(Collections.singletonList(hardSkillDto)));

        userHardSkills = new ArrayList<>();
        userHardSkills.add(hardSkill);
    }

    @Test
    public void getHardSkillsByUser_shouldReturnSetOfHardSkillDto() {
        user.setHardSkills(new HashSet<>(userHardSkills));
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(hardSkillMapper.toDto(any(HardSkill.class))).thenReturn(hardSkillDto);

        Set<HardSkillDto> result = userHardSkillService.getHardSkillsByUser(1);

        assertEquals(1, result.size());
        assertTrue(result.contains(hardSkillDto));
        verify(userRepo, times(1)).findById(1);
    }

    @Test
    public void addHardSkillToUser_shouldReturnUserDto() {
        user.setHardSkills(new HashSet<>());
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(1)).thenReturn(Optional.of(hardSkill));
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userHardSkillService.addHardSkillToUser(1, 1);

        assertEquals(userDto.getId(), result.getId());
        assertTrue(user.getHardSkills().contains(hardSkill));
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void addHardSkillToUser_shouldThrowResourceNotFoundException_whenUserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userHardSkillService.addHardSkillToUser(1, 1);
        });

        assertEquals("Пользователь не найден с id 1", exception.getMessage());
    }

    @Test
    public void addHardSkillToUser_shouldThrowResourceNotFoundException_whenHardSkillNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userHardSkillService.addHardSkillToUser(1, 1);
        });

        assertEquals("Хардскилл не найден с id 1", exception.getMessage());
    }

    @Test
    public void removeHardSkillFromUser_shouldRemoveHardSkill() {
        user.setHardSkills(new HashSet<>(userHardSkills));
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(1)).thenReturn(Optional.of(hardSkill));
        when(userRepo.save(any(User.class))).thenReturn(user);

        userHardSkillService.removeHardSkillFromUser(1, 1);

        assertFalse(user.getHardSkills().contains(hardSkill));
        verify(userRepo, times(1)).save(user);
    }

    @Test
    public void removeHardSkillFromUser_shouldThrowResourceNotFoundException_whenUserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userHardSkillService.removeHardSkillFromUser(1, 1);
        });

        assertEquals("Пользователь не найден с id 1", exception.getMessage());
    }

    @Test
    public void removeHardSkillFromUser_shouldThrowResourceNotFoundException_whenHardSkillNotFound() {
        user.setHardSkills(new HashSet<>(userHardSkills));
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userHardSkillService.removeHardSkillFromUser(1, 1);
        });

        assertEquals("Хардскилл не найден с id 1", exception.getMessage());
    }

    @Test
    public void removeHardSkillFromUser_shouldThrowIllegalArgumentException_whenHardSkillNotAssociated() {
        user.setHardSkills(new HashSet<>());
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(1)).thenReturn(Optional.of(hardSkill));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userHardSkillService.removeHardSkillFromUser(1, 1);
        });

        assertEquals("Хардскилл не ассоциирован с пользователем.", exception.getMessage());
    }

    @Test
    public void  getUserAndProfessionHardSkills_shouldReturnUserHardSkillsDto() {

        HardSkill skill1 = new HardSkill();
        skill1.setName("Java");
        HardSkill skill2 = new HardSkill();
        skill2.setName("Spring");
        HardSkill skill3 = new HardSkill();
        skill3.setName("Python");

        Profession profession1 = new Profession();
        profession1.setId(1);
        profession1.setMainHardSkills(new HashSet<>(Arrays.asList(skill1, skill2)));


        Profession profession2 = new Profession();
        profession2.setId(2);
        profession2.setMainHardSkills(new HashSet<>(Arrays.asList(skill3)));


        user.setHardSkills(new HashSet<>(Arrays.asList(skill1, skill3)));
        user.setProfession(profession1);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findByUserId(1)).thenReturn(new ArrayList<>(user.getHardSkills()));
        when(hardSkillRepo.findByProfessionId(profession1.getId())).thenReturn(new ArrayList<>(profession1.getMainHardSkills()));

        UserHardSkillsDto result = userHardSkillService.getUserAndProfessionHardSkills(1);

        assertNotNull(result);
        assertEquals(1, result.getCommonHardSkills().size());
        assertEquals(1, result.getRemainingUserHardSkills().size());
    }

    @Test
    public void getUserAndProfessionHardSkills_shouldThrowResourceNotFoundException_whenUserNotFound() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userHardSkillService.getUserAndProfessionHardSkills(1);
        });

        assertEquals("Пользователь не найден с id 1", exception.getMessage());
    }

    @Test
    public void getUserAndProfessionHardSkills_shouldThrowResourceNotFoundException_whenProfessionNotFound() {
        User user = mock(User.class); // Создаем мок объекта User
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(user.getProfession()).thenReturn(null); // Теперь это вызов на мок-объекте

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userHardSkillService.getUserAndProfessionHardSkills(1);
        });

        assertEquals("Профессия не найдена для пользователя с id 1", exception.getMessage());
    }

    @Test
    public void getUserAndProfessionHardSkills_withProfessionId_shouldReturnUserHardSkillsDto() {
        user.setHardSkills(new HashSet<>(userHardSkills));
        when(userRepo.findById(1)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findByUserId(1)).thenReturn(userHardSkills);
        when(hardSkillRepo.findByProfessionId(1)).thenReturn(userHardSkills); // Предположим, что у профессии те же навыки для простоты

        UserHardSkillsDto result = userHardSkillService.getUserAndProfessionHardSkills(1, 1);

        assertNotNull(result);
        assertEquals(1, result.getCommonHardSkills().size());
        assertEquals(0, result.getRemainingUserHardSkills().size());
    }
}

package com.t1.profile.skill.hard.service;

import com.t1.profile.skill.hard.exception.HardSkillNotFoundException;
import com.t1.profile.skill.hard.exception.UserHardSkillAssociationAlreadyExistException;
import com.t1.profile.skill.hard.exception.UserHardSkillAssociationNotFoundException;
import com.t1.profile.skill.hard.exception.UserHardSkillNotFoundException;
import com.t1.profile.skill.hard.service.UserHardSkillServiceImpl;
import com.t1.profile.user.dto.UserDto;
import com.t1.profile.skill.hard.dto.UserHardSkillDto;
import com.t1.profile.skill.hard.dto.UserHardSkillsCategorizedDto;
import com.t1.profile.skill.hard.mapper.HardSkillMapper;
import com.t1.profile.skill.hard.mapper.UserHardSkillMapper;
import com.t1.profile.user.mapper.UserMapper;
import com.t1.profile.skill.hard.model.HardSkill;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.user.model.User;
import com.t1.profile.skill.hard.model.UserHardSkill;
import com.t1.profile.skill.hard.repository.HardSkillRepo;
import com.t1.profile.profession.repository.ProfessionRepo;
import com.t1.profile.skill.hard.repository.UserHardSkillRepo;
import com.t1.profile.user.repository.UserRepo;
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
    private UserHardSkillRepo userHardSkillRepo;

    @Mock
    private ProfessionRepo professionRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private UserHardSkillMapper userHardSkillMapper;

    @Mock
    private HardSkillMapper hardSkillMapper;

    @Mock
    private UserMapper userMapper;

    private User user;
    private HardSkill hardSkill;
    private UserHardSkill userHardSkill;
    private UserHardSkillDto userHardSkillDto;
    private UserDto userDto;
    private List<UserHardSkill> userHardSkills;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUserHardSkills(new HashSet<>());
        user.setProfession(new Profession());

        hardSkill = new HardSkill();
        hardSkill.setId(1L);
        hardSkill.setName("Java");

        userHardSkill = new UserHardSkill();
        userHardSkill.setId(1L);
        userHardSkill.setUser(user);
        userHardSkill.setHardSkill(hardSkill);
        userHardSkill.setRating(5);

        userHardSkillDto = new UserHardSkillDto();
        userHardSkillDto.setId(1L);
        userHardSkillDto.setHardSkill(hardSkillMapper.toDto(hardSkill));
        userHardSkillDto.setRating(5);

        userDto = new UserDto();
        userDto.setId(1L);
        UserHardSkillDto userHardSkillDto = new UserHardSkillDto();
        userDto.setUserHardSkills(new HashSet<>(Collections.singletonList(userHardSkillDto)));

        userHardSkills = new ArrayList<>();
        userHardSkills.add(userHardSkill);
    }

    @Test
    public void getHardSkillsByUser_shouldReturnSetOfUserHardSkillDto() {
        user.setUserHardSkills(new HashSet<>(userHardSkills));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userHardSkillMapper.toDto(any(UserHardSkill.class))).thenReturn(userHardSkillDto);

        Set<UserHardSkillDto> result = userHardSkillService.getHardSkillsByUser(1L);

        assertEquals(1, result.size());
        assertTrue(result.contains(userHardSkillDto));
        verify(userRepo, times(1)).findById(1L);
    }

    @Test
    public void updateHardSkillRating_shouldReturnUpdatedUserHardSkillDto1Id() {
        // Настройка мока
        when(userHardSkillRepo.findById(1L)).thenReturn(Optional.of(userHardSkill));
        when(userHardSkillMapper.toDto(userHardSkill)).thenReturn(userHardSkillDto);

        // Вызов тестируемого метода
        userHardSkillService.updateHardSkillRating(1L, 10);

        //userHardSkillDto.setRating(10);

        // Проверка результатов
        assertEquals(10, userHardSkill.getRating());
        verify(userHardSkillRepo, times(1)).save(userHardSkill);

    }

    @Test
    public void updateHardSkillRating_shouldThrowUserHardSkillNotFoundException() {
        when(userHardSkillRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserHardSkillNotFoundException.class, () -> userHardSkillService.updateHardSkillRating(1L, 10));
    }

    @Test
    public void removeHardSkillFromUser_shouldRemoveHardSkill1Id() {
        // Настройка мока
        when(userHardSkillRepo.findById(1L)).thenReturn(Optional.of(userHardSkill));

        User user = new User();
        user.setId(1L);
        user.getUserHardSkills().add(userHardSkill);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        userHardSkillService.removeHardSkillFromUser(1L);

        verify(userHardSkillRepo, times(1)).delete(userHardSkill);
        assertFalse(user.getUserHardSkills().contains(userHardSkill));
    }

    @Test
    public void removeHardSkillFromUser_shouldThrowUserHardSkillAssociationNotFoundException() {
        when(userHardSkillRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserHardSkillAssociationNotFoundException.class, () -> userHardSkillService.removeHardSkillFromUser(1L));
    }

    @Test
    public void removeHardSkillFromUser_shouldThrowUserHardSkillNotFoundExceptionForUser() {
        when(userHardSkillRepo.findById(1L)).thenReturn(Optional.of(userHardSkill));
        when(userRepo.findById(1L)).thenReturn(Optional.empty());


        assertThrows(UserHardSkillNotFoundException.class, () -> userHardSkillService.removeHardSkillFromUser(1L));
    }

    @Test
    public void getHardSkillsByUser_shouldThrowUserHardSkillNotFoundException_whenUserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserHardSkillNotFoundException.class, () -> userHardSkillService.getHardSkillsByUser(1L));

        assertEquals("Пользователь не найден с id 1", exception.getMessage());
    }

    @Test
    public void addHardSkillToUser_shouldReturnUserDto() {
        user.setUserHardSkills(new HashSet<>());

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(1L)).thenReturn(Optional.of(hardSkill));
        when(userHardSkillRepo.findByUserId(1L)).thenReturn(Collections.emptyList());
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userHardSkillService.addHardSkillToUser(1L, 1L, 5);

        assertEquals(userDto.getId(), result.getId());
        verify(userHardSkillRepo, times(1)).save(any(UserHardSkill.class));
    }

    @Test
    public void addHardSkillToUser_shouldThrowUserHardSkillNotFoundException_whenUserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserHardSkillNotFoundException.class, () -> userHardSkillService.addHardSkillToUser(1L, 1L, 5));

        assertEquals("Пользователь не найден с id 1", exception.getMessage());
    }

    @Test
    public void addHardSkillToUser_shouldThrowUserHardSkillNotFoundException_whenHardSkillNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserHardSkillNotFoundException.class, () -> userHardSkillService.addHardSkillToUser(1L, 1L, 5));

        assertEquals("Хардскилл не найден с id 1", exception.getMessage());
    }

    @Test
    public void addHardSkillToUser_shouldThrowUserHardSkillAssociationAlreadyExistException_whenHardSkillAlreadyAssociated() {
        user.setUserHardSkills(new HashSet<>(userHardSkills));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(hardSkillRepo.findById(1L)).thenReturn(Optional.of(hardSkill));
        when(userHardSkillRepo.findByUserId(1L)).thenReturn(userHardSkills);

        Exception exception = assertThrows(UserHardSkillAssociationAlreadyExistException.class, () -> userHardSkillService.addHardSkillToUser(1L, 1L, 5));

        assertEquals("Хардскилл уже ассоциирован с пользователем.", exception.getMessage());
    }

    @Test
    public void updateHardSkillRating_shouldReturnUpdatedUserHardSkillDto() {
        userHardSkill.setRating(5);

        when(userHardSkillRepo.findByUserId(1L)).thenReturn(Collections.singletonList(userHardSkill));
        when(userHardSkillMapper.toDto(userHardSkill)).thenReturn(userHardSkillDto);

        userHardSkillService.updateHardSkillRating(1L, 1L, 10);

        assertEquals(10, userHardSkill.getRating());
        verify(userHardSkillRepo, times(1)).save(userHardSkill);

    }

    @Test
    public void updateHardSkillRating_shouldThrowUserHardSkillAssociationNotFoundException_whenHardSkillNotAssociated() {
        when(userHardSkillRepo.findByUserId(1L)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(UserHardSkillAssociationNotFoundException.class, () -> userHardSkillService.updateHardSkillRating(1L, 1L, 10));

        assertEquals("Хардскилл не ассоциирован с пользователем.", exception.getMessage());
    }

    @Test
    public void removeHardSkillFromUser_shouldRemoveHardSkill() {
        user.setUserHardSkills(new HashSet<>(userHardSkills));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userHardSkillRepo.findByUserId(1L)).thenReturn(userHardSkills);

        userHardSkillService.removeHardSkillFromUser(1L, 1L);

        assertFalse(user.getUserHardSkills().contains(userHardSkill));
        verify(userHardSkillRepo, times(1)).delete(userHardSkill);
    }

    @Test
    public void removeHardSkillFromUser_shouldThrowUserHardSkillNotFoundException_whenUserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserHardSkillNotFoundException.class, () -> userHardSkillService.removeHardSkillFromUser(1L, 1L));

        assertEquals("Пользователь не найден с id 1", exception.getMessage());
    }

    @Test
    public void removeHardSkillFromUser_shouldThrowUserHardSkillAssociationNotFoundException_whenHardSkillNotAssociated() {
        user.setUserHardSkills(new HashSet<>());
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userHardSkillRepo.findByUserId(1L)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(UserHardSkillAssociationNotFoundException.class, () -> userHardSkillService.removeHardSkillFromUser(1L, 1L));

        assertEquals("Хардскилл не ассоциирован с пользователем.", exception.getMessage());
    }

    @Test
    public void getUserAndProfessionHardSkills_shouldReturnUserHardSkillsCategorizedDto() {
        Profession profession = new Profession();
        profession.setId(1L);
        profession.setMainHardSkills(new HashSet<>(Collections.singletonList(hardSkill)));

        user.setProfession(profession);
        user.setUserHardSkills(new HashSet<>(userHardSkills));

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(professionRepo.findById(1L)).thenReturn(Optional.of(profession));
        when(hardSkillRepo.findByProfessionId(1L)).thenReturn(Collections.singletonList(hardSkill));
        when(userHardSkillRepo.findByUserId(1L)).thenReturn(userHardSkills);

        UserHardSkillsCategorizedDto result = userHardSkillService.getUserAndProfessionHardSkills(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.getCommonHardSkills().size());
        assertEquals(0, result.getRemainingUserHardSkills().size());
    }

    @Test
    public void getUserAndProfessionHardSkills_shouldThrowUserHardSkillNotFoundException_whenUserNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserHardSkillNotFoundException.class, () -> userHardSkillService.getUserAndProfessionHardSkills(1L, 1L));

        assertEquals("Пользователь не найден с id 1", exception.getMessage());
    }

    @Test
    public void getUserAndProfessionHardSkills_shouldThrowUserHardSkillNotFoundException_whenProfessionNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(new User()));

        when(professionRepo.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserHardSkillNotFoundException.class, () -> userHardSkillService.getUserAndProfessionHardSkills(1L, 999L));

        assertEquals("Профессия не найдена для пользователя с id 1", exception.getMessage());
    }

    @Test
    public void getUserAndProfessionHardSkills_shouldReturnCategorizedHardSkills_whenNoCommonSkills() {
        Profession profession = new Profession();
        profession.setId(1L);

        user.setProfession(profession);
        user.setUserHardSkills(new HashSet<>(Collections.singletonList(userHardSkill)));

        HardSkill differentSkill = new HardSkill();
        differentSkill.setId(2L);
        differentSkill.setName("Python");

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(professionRepo.findById(1L)).thenReturn(Optional.of(profession));
        when(hardSkillRepo.findByProfessionId(1L)).thenReturn(Collections.singletonList(differentSkill));
        when(userHardSkillRepo.findByUserId(1L)).thenReturn(Collections.singletonList(userHardSkill));

        UserHardSkillsCategorizedDto result = userHardSkillService.getUserAndProfessionHardSkills(1L, 1L);

        assertNotNull(result);
        assertEquals(0, result.getCommonHardSkills().size());
        assertEquals(1, result.getRemainingUserHardSkills().size());
    }

}

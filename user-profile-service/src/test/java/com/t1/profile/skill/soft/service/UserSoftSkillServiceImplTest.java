package com.t1.profile.skill.soft.service;

import com.t1.profile.skill.soft.exception.UserSoftSkillNotFoundException;
import com.t1.profile.skill.soft.mapper.UserSoftSkillMapper;
import com.t1.profile.skill.soft.dto.SoftSkillCategoryWithRatingsDto;
import com.t1.profile.skill.soft.dto.SoftSkillDto;
import com.t1.profile.skill.soft.dto.UserSoftSkillRequestDto;
import com.t1.profile.skill.soft.dto.UserSoftSkillResponseDto;
import com.t1.profile.skill.soft.model.SoftSkill;
import com.t1.profile.skill.soft.model.SoftSkillCategory;
import com.t1.profile.skill.soft.model.UserSoftSkill;
import com.t1.profile.skill.soft.model.UserSoftSkillRating;
import com.t1.profile.skill.soft.repository.CategorySoftSkillRepo;
import com.t1.profile.skill.soft.repository.SoftSkillRepo;
import com.t1.profile.skill.soft.repository.UserSoftSkillRatingRepo;
import com.t1.profile.skill.soft.repository.UserSoftSkillRepo;
import com.t1.profile.user.dto.UserSummaryDto;
import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class  UserSoftSkillServiceImplTest {

    @InjectMocks
    private UserSoftSkillServiceImpl userSoftSkillService;

    @Mock
    private UserSoftSkillRepo userSoftSkillRepo;

    @Mock
    private SoftSkillRepo softSkillRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private CategorySoftSkillRepo categorySoftSkillRepo;

    @Mock
    private UserSoftSkillRatingRepo userSoftSkillRatingRepo;

    @Mock
    private UserSoftSkillMapper userSoftSkillMapper;

    private SoftSkill softSkill;
    private User ratedUser;
    private User raterUser;
    private UserSoftSkillResponseDto ratingDto;
    private UserSoftSkillRequestDto ratingRequestDto;
    private UserSoftSkill userSoftSkill;
    private UserSoftSkillRating softSkillRating;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        softSkill = new SoftSkill();
        softSkill.setId(1);
        softSkill.setName("Communication");

        ratedUser = new User();
        ratedUser.setId(1);
        ratedUser.setFirstName("User 1");

        raterUser = new User();
        raterUser.setId(2);
        raterUser.setFirstName("User 2");

        ratingDto = new UserSoftSkillResponseDto();
        SoftSkillDto softSkillDto = new SoftSkillDto();
        softSkillDto.setId(1);
        softSkillDto.setName("Communication");

        UserSummaryDto ratedUserDto = new UserSummaryDto();
        ratedUserDto.setId(1);
        ratedUserDto.setFirstName("User 1");

        UserSummaryDto raterUserDto = new UserSummaryDto();
        raterUserDto.setId(2);
        raterUserDto.setFirstName("User 2");

        ratingDto.setSoftSkill(softSkillDto);
        ratingDto.setRatedUser(ratedUserDto);
        ratingDto.setRaterUser(raterUserDto);
        ratingDto.setRating(5);

        ratingRequestDto = new UserSoftSkillRequestDto();
        ratingRequestDto.setSoftSkillId(1);
        ratingRequestDto.setRatedUserId(1);
        ratingRequestDto.setRaterUserId(2);
        ratingRequestDto.setRating(5);

        userSoftSkill = new UserSoftSkill();
        userSoftSkill.setId(1);
        userSoftSkill.setSoftSkill(softSkill);
        userSoftSkill.setRatedUser(ratedUser);
        userSoftSkill.setRaterUser(raterUser);
        userSoftSkill.setRating(5);

        softSkillRating = new UserSoftSkillRating();
        softSkillRating.setAverageRating(5.0);
        softSkillRating.setRatedUser(ratedUser);
        softSkillRating.setSoftSkill(softSkill);
    }

    @Test
    public void rateSoftSkill_shouldReturnSoftSkillRatingDto() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(1)).thenReturn(Optional.of(ratedUser));
        when(userRepo.findById(2)).thenReturn(Optional.of(raterUser));
        when(userSoftSkillRepo.save(any(UserSoftSkill.class))).thenReturn(userSoftSkill);
        when(userSoftSkillMapper.toDto(any(UserSoftSkill.class))).thenReturn(ratingDto);

        UserSoftSkillResponseDto result = userSoftSkillService.rateSoftSkill(ratingRequestDto);

        assertEquals(ratingDto.getRating(), result.getRating());
        assertEquals(ratingDto.getSoftSkill().getId(), result.getSoftSkill().getId());
        assertEquals(ratingDto.getRatedUser().getId(), result.getRatedUser().getId());
        assertEquals(ratingDto.getRaterUser().getId(), result.getRaterUser().getId());
        verify(userSoftSkillRepo, times(1)).save(any(UserSoftSkill.class));
        verify(userSoftSkillRatingRepo, times(1)).save(any(UserSoftSkillRating.class)); // Проверяем сохранение рейтинга
    }
    @Test
    public void rateSoftSkill_shouldThrowUserSoftSkillNotFoundException_whenSoftSkillNotFound() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserSoftSkillNotFoundException.class, () -> userSoftSkillService.rateSoftSkill(ratingRequestDto));

        assertEquals("SoftSkill not found с id 1", exception.getMessage());
    }

    @Test
    public void rateSoftSkill_shouldThrowUserSoftSkillNotFoundException_whenRatedUserNotFound() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserSoftSkillNotFoundException.class, () -> userSoftSkillService.rateSoftSkill(ratingRequestDto));

        assertEquals("Rated user not found с id 1", exception.getMessage());
    }

    @Test
    public void rateSoftSkill_shouldThrowUserSoftSkillNotFoundException_whenRaterUserNotFound() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(1)).thenReturn(Optional.of(ratedUser));
        when(userRepo.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserSoftSkillNotFoundException.class, () -> userSoftSkillService.rateSoftSkill(ratingRequestDto));

        assertEquals("Rater user not found 2", exception.getMessage());
    }

    @Test
    public void getRatingBySoftSkill_shouldReturnListOfSoftSkillRatingDto() {
        List<UserSoftSkill> ratings = Collections.singletonList(userSoftSkill);

        when(userSoftSkillRepo.findRatingsBySoftSkillId(1)).thenReturn(ratings);
        when(userSoftSkillMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(ratingDto));

        List<UserSoftSkillResponseDto> result = userSoftSkillService.getRatingBySoftSkill(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ratingDto.getRating(), result.get(0).getRating());
        assertEquals(ratingDto.getSoftSkill().getId(), result.get(0).getSoftSkill().getId());
        verify(userSoftSkillRepo, times(1)).findRatingsBySoftSkillId(1);
    }

    @Test
    public void getSoftSkillsWithRatingsByUser_shouldReturnListOfSoftSkillCategoryWithRatingsDto() {
        // Подготовка данных
        SoftSkillCategory category = new SoftSkillCategory();
        category.setId(1);
        category.setName("Communication Skills");

        when(categorySoftSkillRepo.findAll()).thenReturn(Collections.singletonList(category));
        when(softSkillRepo.findByCategory(category)).thenReturn(Collections.singletonList(softSkill));
        when(userSoftSkillRatingRepo.findByRatedUserAndSoftSkill(any(User.class), any(SoftSkill.class))).thenReturn(softSkillRating);
        when(userRepo.findById(1)).thenReturn(Optional.of(ratedUser));

        List<SoftSkillCategoryWithRatingsDto> result = userSoftSkillService.getSoftSkillsWithRatingsByUser(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(category.getId(), result.get(0).getId());
        assertEquals(category.getName(), result.get(0).getName());
        assertEquals(softSkill.getId(), result.get(0).getSoftSkills().get(0).getId());
        assertEquals(softSkillRating.getAverageRating(), result.get(0).getSoftSkills().get(0).getAverageRating());
    }
    
}
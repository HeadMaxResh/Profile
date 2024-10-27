package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillRatingDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.SoftSkillRatingMapper;
import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.SoftSkillRating;
import com.t1.profile.model.User;
import com.t1.profile.repository.SoftSkillRepo;
import com.t1.profile.repository.UserRepo;
import com.t1.profile.repository.UserSoftSkillRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserSoftSkillServiceImplTest {

    @InjectMocks
    private UserSoftSkillServiceImpl userSoftSkillService;

    @Mock
    private UserSoftSkillRepo userSoftSkillRepo;

    @Mock
    private SoftSkillRepo softSkillRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private SoftSkillRatingMapper softSkillRatingMapper;

    private SoftSkill softSkill;
    private User ratedUser;
    private User raterUser;
    private SoftSkillRatingDto ratingDto;
    private SoftSkillRating softSkillRating;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Инициализация тестовых объектов
        softSkill = new SoftSkill();
        softSkill.setId(1);
        softSkill.setName("Communication");

        ratedUser = new User();
        ratedUser.setId(1);
        ratedUser.setFirstName("User 1");

        raterUser = new User();
        raterUser.setId(2);
        raterUser.setFirstName("User 2");

        ratingDto = new SoftSkillRatingDto();
        ratingDto.setSoftSkillId(1);
        ratingDto.setRatedUserId(1);
        ratingDto.setRaterUserId(2);
        ratingDto.setRating(5);

        softSkillRating = new SoftSkillRating();
        softSkillRating.setId(1);
        softSkillRating.setSoftSkill(softSkill);
        softSkillRating.setRatedUser(ratedUser);
        softSkillRating.setRaterUser(raterUser);
        softSkillRating.setRating(5);
    }

    @Test
    public void rateSoftSkill_shouldReturnSoftSkillRatingDto() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(1)).thenReturn(Optional.of(ratedUser));
        when(userRepo.findById(2)).thenReturn(Optional.of(raterUser));
        when(userSoftSkillRepo.save(any(SoftSkillRating.class))).thenReturn(softSkillRating);
        when(softSkillRatingMapper.toDto(any(SoftSkillRating.class))).thenReturn(ratingDto);

        SoftSkillRatingDto result = userSoftSkillService.rateSoftSkill(ratingDto);

        assertEquals(ratingDto.getRating(), result.getRating());
        assertEquals(ratingDto.getSoftSkillId(), result.getSoftSkillId());
        verify(userSoftSkillRepo, times(1)).save(any(SoftSkillRating.class));
    }

    @Test
    public void rateSoftSkill_shouldThrowResourceNotFoundException_whenSoftSkillNotFound() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userSoftSkillService.rateSoftSkill(ratingDto);
        });

        assertEquals("SoftSkill not found с id 1", exception.getMessage());
    }

    @Test
    public void rateSoftSkill_shouldThrowResourceNotFoundException_whenRatedUserNotFound() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userSoftSkillService.rateSoftSkill(ratingDto);
        });

        assertEquals("Rated user not found с id 1", exception.getMessage());
    }

    @Test
    public void rateSoftSkill_shouldThrowResourceNotFoundException_whenRaterUserNotFound() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(1)).thenReturn(Optional.of(ratedUser));
        when(userRepo.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userSoftSkillService.rateSoftSkill(ratingDto);
        });

        assertEquals("Rater user not found 2", exception.getMessage());
    }

    @Test
    public void getRatingBySoftSkill_shouldReturnListOfSoftSkillRatingDto() {
        List<SoftSkillRating> ratings = new ArrayList<>();
        ratings.add(softSkillRating);

        when(userSoftSkillRepo.findRatingsBySoftSkillId(1)).thenReturn(ratings);
        when(softSkillRatingMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(ratingDto));

        List<SoftSkillRatingDto> result = userSoftSkillService.getRatingBySoftSkill(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ratingDto.getRating(), result.get(0).getRating());
        verify(userSoftSkillRepo, times(1)).findRatingsBySoftSkillId(1);
    }

}

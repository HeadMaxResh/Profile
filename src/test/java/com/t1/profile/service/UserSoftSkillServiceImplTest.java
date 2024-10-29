package com.t1.profile.service;

import com.t1.profile.dto.UserSoftSkillDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.UserSoftSkillMapper;
import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.UserSoftSkill;
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
    private UserSoftSkillMapper userSoftSkillMapper;

    private SoftSkill softSkill;
    private User ratedUser;
    private User raterUser;
    private UserSoftSkillDto ratingDto;
    private UserSoftSkill userSoftSkill;

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

        ratingDto = new UserSoftSkillDto();
        ratingDto.setSoftSkillId(1);
        ratingDto.setRatedUserId(1);
        ratingDto.setRaterUserId(2);
        ratingDto.setRating(5);

        userSoftSkill = new UserSoftSkill();
        userSoftSkill.setId(1);
        userSoftSkill.setSoftSkill(softSkill);
        userSoftSkill.setRatedUser(ratedUser);
        userSoftSkill.setRaterUser(raterUser);
        userSoftSkill.setRating(5);
    }

    @Test
    public void rateSoftSkill_shouldReturnSoftSkillRatingDto() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(1)).thenReturn(Optional.of(ratedUser));
        when(userRepo.findById(2)).thenReturn(Optional.of(raterUser));
        when(userSoftSkillRepo.save(any(UserSoftSkill.class))).thenReturn(userSoftSkill);
        when(userSoftSkillMapper.toDto(any(UserSoftSkill.class))).thenReturn(ratingDto);

        UserSoftSkillDto result = userSoftSkillService.rateSoftSkill(ratingDto);

        assertEquals(ratingDto.getRating(), result.getRating());
        assertEquals(ratingDto.getSoftSkillId(), result.getSoftSkillId());
        verify(userSoftSkillRepo, times(1)).save(any(UserSoftSkill.class));
    }

    @Test
    public void rateSoftSkill_shouldThrowResourceNotFoundException_whenSoftSkillNotFound() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userSoftSkillService.rateSoftSkill(ratingDto));

        assertEquals("SoftSkill not found с id 1", exception.getMessage());
    }

    @Test
    public void rateSoftSkill_shouldThrowResourceNotFoundException_whenRatedUserNotFound() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userSoftSkillService.rateSoftSkill(ratingDto));

        assertEquals("Rated user not found с id 1", exception.getMessage());
    }

    @Test
    public void rateSoftSkill_shouldThrowResourceNotFoundException_whenRaterUserNotFound() {
        when(softSkillRepo.findById(1)).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(1)).thenReturn(Optional.of(ratedUser));
        when(userRepo.findById(2)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userSoftSkillService.rateSoftSkill(ratingDto));

        assertEquals("Rater user not found 2", exception.getMessage());
    }

    @Test
    public void getRatingBySoftSkill_shouldReturnListOfSoftSkillRatingDto() {
        List<UserSoftSkill> ratings = new ArrayList<>();
        ratings.add(userSoftSkill);

        when(userSoftSkillRepo.findRatingsBySoftSkillId(1)).thenReturn(ratings);
        when(userSoftSkillMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(ratingDto));

        List<UserSoftSkillDto> result = userSoftSkillService.getRatingBySoftSkill(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ratingDto.getRating(), result.get(0).getRating());
        verify(userSoftSkillRepo, times(1)).findRatingsBySoftSkillId(1);
    }

}

package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillRatingDto;
import com.t1.profile.exeption.ResourceNotFoundException;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRateSoftSkill() {
        SoftSkillRatingDto ratingDto = new SoftSkillRatingDto();
        ratingDto.setSoftSkillId(1);
        ratingDto.setRatedUserId(2);
        ratingDto.setRaterUserId(3);
        ratingDto.setRating(5);

        SoftSkill softSkill = new SoftSkill();
        softSkill.setId(1);
        User ratedUser = new User();
        ratedUser.setId(2);
        User raterUser = new User();
        raterUser.setId(3);

        when(softSkillRepo.findById(ratingDto.getSoftSkillId())).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(ratingDto.getRatedUserId())).thenReturn(Optional.of(ratedUser));
        when(userRepo.findById(ratingDto.getRaterUserId())).thenReturn(Optional.of(raterUser));
        when(userSoftSkillRepo.save(any(SoftSkillRating.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SoftSkillRating savedRating = userSoftSkillService.rateSoftSkill(ratingDto);

        assertThat(savedRating).isNotNull();
        assertThat(savedRating.getSoftSkill()).isEqualTo(softSkill);
        assertThat(savedRating.getRatedUser()).isEqualTo(ratedUser);
        assertThat(savedRating.getRaterUser()).isEqualTo(raterUser);
        assertThat(savedRating.getRating()).isEqualTo(5);

        verify(softSkillRepo, times(1)).findById(ratingDto.getSoftSkillId());
        verify(userRepo, times(1)).findById(ratingDto.getRatedUserId());
        verify(userRepo, times(1)).findById(ratingDto.getRaterUserId());
        verify(userSoftSkillRepo, times(1)).save(any(SoftSkillRating.class));
    }

    @Test
    public void testRateSoftSkill_SoftSkillNotFound() {
        SoftSkillRatingDto ratingDto = new SoftSkillRatingDto();
        ratingDto.setSoftSkillId(1);
        ratingDto.setRatedUserId(2);
        ratingDto.setRaterUserId(3);
        ratingDto.setRating(5);

        when(softSkillRepo.findById(ratingDto.getSoftSkillId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userSoftSkillService.rateSoftSkill(ratingDto);
        });

        assertThat(exception.getMessage()).isEqualTo("SoftSkill not found с id " + ratingDto.getSoftSkillId());
        verify(softSkillRepo, times(1)).findById(ratingDto.getSoftSkillId());
        verify(userRepo, never()).findById(anyInt());
        verify(userSoftSkillRepo, never()).save(any(SoftSkillRating.class));
    }

    @Test
    public void testRateSoftSkill_RatedUserNotFound() {
        SoftSkillRatingDto ratingDto = new SoftSkillRatingDto();
        ratingDto.setSoftSkillId(1);
        ratingDto.setRatedUserId(2);
        ratingDto.setRaterUserId(3);
        ratingDto.setRating(5);

        SoftSkill softSkill = new SoftSkill();
        softSkill.setId(1);

        when(softSkillRepo.findById(ratingDto.getSoftSkillId())).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(ratingDto.getRatedUserId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userSoftSkillService.rateSoftSkill(ratingDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Rated user not found с id " + ratingDto.getRatedUserId());
        verify(softSkillRepo, times(1)).findById(ratingDto.getSoftSkillId());
        verify(userRepo, times(1)).findById(ratingDto.getRatedUserId());
        verify(userRepo, never()).findById(ratingDto.getRaterUserId());
        verify(userSoftSkillRepo, never()).save(any(SoftSkillRating.class));
    }

    @Test
    public void testRateSoftSkill_RaterUserNotFound() {
        SoftSkillRatingDto ratingDto = new SoftSkillRatingDto();
        ratingDto.setSoftSkillId(1);
        ratingDto.setRatedUserId(2);
        ratingDto.setRaterUserId(3);
        ratingDto.setRating(5);

        SoftSkill softSkill = new SoftSkill();
        softSkill.setId(1);
        User ratedUser = new User();
        ratedUser.setId(2);

        when(softSkillRepo.findById(ratingDto.getSoftSkillId())).thenReturn(Optional.of(softSkill));
        when(userRepo.findById(ratingDto.getRatedUserId())).thenReturn(Optional.of(ratedUser));
        when(userRepo.findById(ratingDto.getRaterUserId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userSoftSkillService.rateSoftSkill(ratingDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Rater user not found " + ratingDto.getRaterUserId());
        verify(softSkillRepo, times(1)).findById(ratingDto.getSoftSkillId());
        verify(userRepo, times(1)).findById(ratingDto.getRatedUserId());
        verify(userRepo, times(1)).findById(ratingDto.getRaterUserId());
        verify(userSoftSkillRepo, never()).save(any(SoftSkillRating.class));
    }

    @Test
    public void testGetRatingBySoftSkill() {
        Integer softSkillId = 1;
        SoftSkillRating rating1 = new SoftSkillRating();
        rating1.setRating(5);
        SoftSkillRating rating2 = new SoftSkillRating();
        rating2.setRating(4);

        when(userSoftSkillRepo.findRatingsBySoftSkillId(softSkillId)).thenReturn(Arrays.asList(rating1, rating2));

        List<SoftSkillRating> ratings = userSoftSkillService.getRatingBySoftSkill(softSkillId);

        assertThat(ratings).hasSize(2);
        assertThat(ratings).containsExactlyInAnyOrder(rating1, rating2);
        verify(userSoftSkillRepo, times(1)).findRatingsBySoftSkillId(softSkillId);
    }

}

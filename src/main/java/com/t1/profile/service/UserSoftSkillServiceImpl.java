package com.t1.profile.service;

import com.t1.profile.dto.*;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.SoftSkillCategoryMapper;
import com.t1.profile.mapper.UserSoftSkillMapper;
import com.t1.profile.model.*;
import com.t1.profile.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Service
public class UserSoftSkillServiceImpl implements UserSoftSkillService {

    @Autowired
    private UserSoftSkillRepo userSoftSkillRepo;

    @Autowired
    private SoftSkillRepo softSkillRepo;

    @Autowired
    private CategorySoftSkillRepo categorySoftSkillRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserSoftSkillRatingRepo userSoftSkillRatingRepo;

    @Autowired
    private UserSoftSkillMapper userSoftSkillMapper;

    @Autowired
    private SoftSkillCategoryMapper softSkillCategoryMapper;

    @Override
    public UserSoftSkillResponseDto rateSoftSkill(UserSoftSkillRequestDto ratingDto) {
        SoftSkill softSkill = softSkillRepo.findById(ratingDto.getSoftSkillId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "SoftSkill not found с id " + ratingDto.getSoftSkillId())
                );

        User ratedUser = userRepo.findById(ratingDto.getRatedUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Rated user not found с id " + ratingDto.getRatedUserId())
                );

        User raterUser = userRepo.findById(ratingDto.getRaterUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Rater user not found " + ratingDto.getRaterUserId())
                );

        UserSoftSkill rating = new UserSoftSkill();
        rating.setSoftSkill(softSkill);
        rating.setRatedUser(ratedUser);
        rating.setRaterUser(raterUser);
        rating.setRating(ratingDto.getRating());
        UserSoftSkill savedUserSoftSkill = userSoftSkillRepo.save(rating);

        updateSoftSkillRating(ratedUser, softSkill);

        return userSoftSkillMapper.toDto(savedUserSoftSkill);
    }

    @Override
    public List<UserSoftSkillResponseDto> rateMultipleSoftSkills(UserSoftSkillBatchRequestDto batchRequestDto) {
        List<UserSoftSkillResponseDto> responseDtos = new ArrayList<>();

        for (UserSoftSkillRequestDto ratingDto : batchRequestDto.getRatings()) {
            responseDtos.add(rateSoftSkill(ratingDto));
        }

        return responseDtos;
    }

    @Override
    public List<UserSoftSkillResponseDto> getRatingBySoftSkill(Integer softSkillId) {
        return userSoftSkillMapper.toDtoList(userSoftSkillRepo.findRatingsBySoftSkillId(softSkillId));
    }

    @Override
    public List<SoftSkillCategoryWithRatingsDto> getSoftSkillsWithRatingsByUser(Integer userId) {
        List<SoftSkillCategory> categories = categorySoftSkillRepo.findAll();

        List<SoftSkillCategoryWithRatingsDto> result = new ArrayList<>();

        for (SoftSkillCategory category : categories) {
            SoftSkillCategoryWithRatingsDto categoryDto = new SoftSkillCategoryWithRatingsDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());

            List<SoftSkillWithAverageRatingDto> skillsWithRatings = new ArrayList<>();

            List<SoftSkill> softSkills = softSkillRepo.findByCategory(category);
            for (SoftSkill softSkill : softSkills) {
                UserSoftSkillRating softSkillRating = userSoftSkillRatingRepo.findByRatedUserAndSoftSkill(
                        userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found")),
                        softSkill
                );

                SoftSkillWithAverageRatingDto skillDto = new SoftSkillWithAverageRatingDto();
                skillDto.setId(softSkill.getId());
                skillDto.setName(softSkill.getName());
                skillDto.setAverageRating(softSkillRating != null ? softSkillRating.getAverageRating() : null);

                skillsWithRatings.add(skillDto);
            }

            categoryDto.setSoftSkills(skillsWithRatings);
            result.add(categoryDto);
        }

        return result;
    }

    @Override
    public void deleteUserSoftSkill(Integer userSoftSkillId) {
        UserSoftSkill userSoftSkill = userSoftSkillRepo.findById(userSoftSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("UserSoftSkill not found с id " + userSoftSkillId));

        userSoftSkillRepo.delete(userSoftSkill);

        updateSoftSkillRating(userSoftSkill.getRatedUser(), userSoftSkill.getSoftSkill());
    }

    @Override
    public void deleteAllUserSoftSkillsByUserId(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found с id " + userId));

        List<UserSoftSkill> userSoftSkills = userSoftSkillRepo.findByRatedUser(user);

        for (UserSoftSkill userSoftSkill : userSoftSkills) {
            userSoftSkillRepo.delete(userSoftSkill);
            updateSoftSkillRating(user, userSoftSkill.getSoftSkill());
        }
    }

    private void updateSoftSkillRating(User ratedUser, SoftSkill softSkill) {
        UserSoftSkillRating softSkillRating = userSoftSkillRatingRepo.findByRatedUserAndSoftSkill(ratedUser, softSkill);

        List<UserSoftSkill> ratings = userSoftSkillRepo.findRatingsBySoftSkillId(softSkill.getId());

        OptionalDouble averageOpt = ratings.stream()
                .mapToInt(UserSoftSkill::getRating)
                .average();

        Double average = averageOpt.isPresent() ? averageOpt.getAsDouble() : null;

        if (softSkillRating == null) {
            softSkillRating = new UserSoftSkillRating();
            softSkillRating.setRatedUser(ratedUser);
            softSkillRating.setSoftSkill(softSkill);
        }

        softSkillRating.setAverageRating(average);
        userSoftSkillRatingRepo.save(softSkillRating);
    }

}
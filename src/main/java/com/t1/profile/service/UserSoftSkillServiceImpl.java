package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryWithRatingsDto;
import com.t1.profile.dto.SoftSkillWithAverageRatingDto;
import com.t1.profile.dto.UserSoftSkillResponseDto;
import com.t1.profile.dto.UserSoftSkillRequestDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.SoftSkillCategoryMapper;
import com.t1.profile.mapper.UserSoftSkillMapper;
import com.t1.profile.model.*;
import com.t1.profile.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

                /*Double averageRating =
                        userSoftSkillRepo.findAverageRatingByUserAndSoftSkill(userId, softSkill.getId());*/

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

    private void updateSoftSkillRating(User ratedUser, SoftSkill softSkill) {
        UserSoftSkillRating softSkillRating = userSoftSkillRatingRepo.findByRatedUserAndSoftSkill(ratedUser, softSkill);

        List<UserSoftSkill> ratings = userSoftSkillRepo.findRatingsBySoftSkillId(softSkill.getId());
        Double average = ratings.stream()
                .mapToInt(UserSoftSkill::getRating)
                .average()
                .orElse(0.0);

        if (softSkillRating == null) {
            softSkillRating = new UserSoftSkillRating();
            softSkillRating.setRatedUser(ratedUser);
            softSkillRating.setSoftSkill(softSkill);
        }

        softSkillRating.setAverageRating(average);
        userSoftSkillRatingRepo.save(softSkillRating);
    }

}
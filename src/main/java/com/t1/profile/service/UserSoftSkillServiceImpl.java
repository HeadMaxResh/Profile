package com.t1.profile.service;

import com.t1.profile.dto.SoftSkillCategoryWithRatingsDto;
import com.t1.profile.dto.SoftSkillWithAverageRatingDto;
import com.t1.profile.dto.UserSoftSkillDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.SoftSkillCategoryMapper;
import com.t1.profile.mapper.UserSoftSkillMapper;
import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.SoftSkillCategory;
import com.t1.profile.model.User;
import com.t1.profile.model.UserSoftSkill;
import com.t1.profile.repository.CategorySoftSkillRepo;
import com.t1.profile.repository.SoftSkillRepo;
import com.t1.profile.repository.UserRepo;
import com.t1.profile.repository.UserSoftSkillRepo;
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
    private UserSoftSkillMapper userSoftSkillMapper;

    @Autowired
    private SoftSkillCategoryMapper softSkillCategoryMapper;

    @Override
    public UserSoftSkillDto rateSoftSkill(UserSoftSkillDto ratingDto) {
        SoftSkill softSkill = softSkillRepo.findById(ratingDto.getSoftSkill().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "SoftSkill not found с id " + ratingDto.getSoftSkill().getId())
                );

        User ratedUser = userRepo.findById(ratingDto.getRatedUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Rated user not found с id " + ratingDto.getRatedUser().getId())
                );

        User raterUser = userRepo.findById(ratingDto.getRaterUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Rater user not found " + ratingDto.getRaterUser().getId())
                );

        UserSoftSkill rating = new UserSoftSkill();
        rating.setSoftSkill(softSkill);
        rating.setRatedUser(ratedUser);
        rating.setRaterUser(raterUser);
        rating.setRating(ratingDto.getRating());
        UserSoftSkill savedUserSoftSkill = userSoftSkillRepo.save(rating);
        return userSoftSkillMapper.toDto(savedUserSoftSkill);
    }

    @Override
    public List<UserSoftSkillDto> getRatingBySoftSkill(Integer softSkillId) {
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
                Double averageRating =
                        userSoftSkillRepo.findAverageRatingByUserAndSoftSkill(userId, softSkill.getId());

                SoftSkillWithAverageRatingDto skillDto = new SoftSkillWithAverageRatingDto();
                skillDto.setId(softSkill.getId());
                skillDto.setName(softSkill.getName());
                skillDto.setAverageRating(averageRating);

                skillsWithRatings.add(skillDto);
            }

            categoryDto.setSoftSkills(skillsWithRatings);
            result.add(categoryDto);
        }

        return result;
    }

}

package com.t1.profile.skill.soft.service;

import com.t1.profile.skill.soft.dto.*;
import com.t1.profile.skill.soft.exception.SoftSkillNotFoundException;
import com.t1.profile.skill.soft.exception.UserSoftSkillNotFoundException;
import com.t1.profile.skill.soft.mapper.SoftSkillCategoryMapper;
import com.t1.profile.skill.soft.mapper.UserSoftSkillMapper;
import com.t1.profile.skill.soft.model.*;
import com.t1.profile.skill.soft.repository.*;
import com.t1.profile.user.exception.UserNotFoundException;
import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private UserSoftSkillRatingHistoryRepo userSoftSkillRatingHistoryRepo;

    @Autowired
    private UserSoftSkillMapper userSoftSkillMapper;

    @Autowired
    private SoftSkillCategoryMapper softSkillCategoryMapper;

    @Override
    public UserSoftSkillResponseDto rateSoftSkill(UserSoftSkillRequestDto ratingDto) {
        SoftSkill softSkill = softSkillRepo.findById(ratingDto.getSoftSkillId())
                .orElseThrow(() -> new SoftSkillNotFoundException(
                        "SoftSkill not found с id " + ratingDto.getSoftSkillId())
                );

        User ratedUser = userRepo.findById(ratingDto.getRatedUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "Rated user not found с id " + ratingDto.getRatedUserId())
                );

        User raterUser = userRepo.findById(ratingDto.getRaterUserId())
                .orElseThrow(() -> new UserNotFoundException(
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
                User user = userRepo.findById(userId).orElseThrow(()
                        -> new UserNotFoundException("User not found"));
                Double lastHistoryRating = getHistoryRating(user, softSkill);

                UserSoftSkillRating softSkillRating =
                        userSoftSkillRatingRepo.findByRatedUserAndSoftSkill(user, softSkill);
                /*UserSoftSkillRatingHistory historySoftSkillRating =
                        userSoftSkillRatingHistoryRepo.findByRatedUserAndSoftSkill(
                                userRepo.findById(userId).orElseThrow(()
                                        -> new ResourceNotFoundException("User not found")),
                                softSkill
                        );*/


                SoftSkillWithAverageRatingDto skillDto = new SoftSkillWithAverageRatingDto();
                skillDto.setId(softSkill.getId());
                skillDto.setName(softSkill.getName());
                skillDto.setAverageRating(
                        softSkillRating != null ? softSkillRating.getAverageRating() : null
                );
                skillDto.setHistoryRating(lastHistoryRating);
                /*skillDto.setHistoryRating(
                        historySoftSkillRating != null ? historySoftSkillRating.getAverageRating() : null
                );*/

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
                .orElseThrow(() -> new UserSoftSkillNotFoundException("UserSoftSkill not found с id " + userSoftSkillId));

        userSoftSkillRepo.delete(userSoftSkill);

        updateSoftSkillRating(userSoftSkill.getRatedUser(), userSoftSkill.getSoftSkill());
    }

    @Override
    public void deleteAllUserSoftSkillsByUserId(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found с id " + userId));

        List<UserSoftSkill> userSoftSkills = userSoftSkillRepo.findByRatedUser(user);

        for (UserSoftSkill userSoftSkill : userSoftSkills) {
            UserSoftSkillRating softSkillRating = userSoftSkillRatingRepo.findByRatedUserAndSoftSkill(
                    userSoftSkill.getRatedUser(),
                    userSoftSkill.getSoftSkill()
            );

            saveRatingToHistory(
                    userSoftSkill.getRatedUser(),
                    userSoftSkill.getSoftSkill(),
                    softSkillRating
            );

        }
        userSoftSkillRepo.deleteAll(userSoftSkills);

        for (SoftSkill softSkill : softSkillRepo.findAll()) {
            updateSoftSkillRating(user, softSkill);
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

    private void saveRatingToHistory(User ratedUser, SoftSkill softSkill, UserSoftSkillRating softSkillRating) {
        if (softSkillRating != null) {
            UserSoftSkillRatingHistory history = new UserSoftSkillRatingHistory();
            history.setRatedUser(ratedUser);
            history.setSoftSkill(softSkill);
            history.setAverageRating(softSkillRating.getAverageRating());
            history.setDeletedAt(LocalDateTime.now());

            userSoftSkillRatingHistoryRepo.save(history);
        }
    }

    private Double getHistoryRating(User ratedUser, SoftSkill softSkill) {
        List<UserSoftSkillRatingHistory> history =
                userSoftSkillRatingHistoryRepo.findByRatedUserAndSoftSkill(ratedUser, softSkill);
        if (history.isEmpty()) return null;
        UserSoftSkillRatingHistory lastRating = history.get(history.size() - 1);
        return lastRating.getAverageRating();
    }

}
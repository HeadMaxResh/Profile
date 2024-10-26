package com.t1.profile.service;

import com.t1.profile.dto.HardSkillRatingDto;
import com.t1.profile.model.HardSkillRating;
import com.t1.profile.model.HardSkillIndicator;
import com.t1.profile.model.User;
import com.t1.profile.repository.HardSkillIndicatorRepo;
import com.t1.profile.repository.HardSkillRatingRepo;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HardSkillRatingService {

    @Autowired
    private HardSkillRatingRepo hardSkillRatingRepo;

    @Autowired
    private HardSkillIndicatorRepo hardSkillIndicatorRepo;

    @Autowired
    private UserRepo userRepository;

    public HardSkillRating rateHardSkill(HardSkillRatingDto ratingDto) {
        HardSkillIndicator indicator = hardSkillIndicatorRepo.findById(ratingDto.getIndicatorId())
                .orElseThrow(() -> new RuntimeException("Indicator not found"));
        User ratedUser = userRepository.findById(ratingDto.getRatedUserId())
                .orElseThrow(() -> new RuntimeException("Rated user not found"));
        User raterUser = userRepository.findById(ratingDto.getRaterUserId())
                .orElseThrow(() -> new RuntimeException("Rater user not found"));

        HardSkillRating hardSkillRating = new HardSkillRating();
        hardSkillRating.setIndicator(indicator);
        hardSkillRating.setRatedUser(ratedUser);
        hardSkillRating.setRaterUser(raterUser);
        hardSkillRating.setRating(ratingDto.getRating());

        return hardSkillRatingRepo.save(hardSkillRating);
    }
}

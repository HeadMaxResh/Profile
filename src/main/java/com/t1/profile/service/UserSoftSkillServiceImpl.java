package com.t1.profile.service;

import com.t1.profile.dto.UserSoftSkillDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.UserSoftSkillMapper;
import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.UserSoftSkill;
import com.t1.profile.model.User;
import com.t1.profile.repository.UserSoftSkillRepo;
import com.t1.profile.repository.SoftSkillRepo;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSoftSkillServiceImpl implements UserSoftSkillService {

    @Autowired
    private UserSoftSkillRepo userSoftSkillRepo;

    @Autowired
    private SoftSkillRepo softSkillRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserSoftSkillMapper userSoftSkillMapper;

    @Override
    public UserSoftSkillDto rateSoftSkill(UserSoftSkillDto ratingDto) {
        SoftSkill softSkill = softSkillRepo.findById(ratingDto.getSoftSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("SoftSkill not found с id " + ratingDto.getSoftSkillId()));

        User ratedUser = userRepo.findById(ratingDto.getRatedUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Rated user not found с id " + ratingDto.getRatedUserId()));

        User raterUser = userRepo.findById(ratingDto.getRaterUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Rater user not found " + ratingDto.getRaterUserId()));

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
}

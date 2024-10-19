package com.t1.profile.controller;

import com.t1.profile.dto.SoftSkillRatingDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.SoftSkillRating;
import com.t1.profile.model.User;
import com.t1.profile.repository.SoftSkillRatingRepo;
import com.t1.profile.repository.SoftSkillRepo;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soft-skill-rating")
public class SoftSkillRatingController {

    @Autowired
    private SoftSkillRatingRepo softSkillRatingRepo;

    @Autowired
    private SoftSkillRepo softSkillRepo;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/add")
    public ResponseEntity<SoftSkillRating> rateSoftSkill(@RequestBody SoftSkillRatingDto ratingDto) {
        SoftSkill softSkill = softSkillRepo.findById(ratingDto.getSoftSkillId())
                .orElseThrow(() -> new ResourceNotFoundException("SoftSkill not found с id " + ratingDto.getSoftSkillId()));

        User ratedUser = userRepo.findById(ratingDto.getRatedUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Rated user not found с id " + ratingDto.getRatedUserId()));

        User raterUser = userRepo.findById(ratingDto.getRaterUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Rater user not found " + ratingDto.getRaterUserId()));

        SoftSkillRating rating = new SoftSkillRating();
        rating.setSoftSkill(softSkill);
        rating.setRatedUser(ratedUser);
        rating.setRaterUser(raterUser);
        rating.setRating(ratingDto.getRating());

        SoftSkillRating savedRating = softSkillRatingRepo.save(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @GetMapping("/soft-skill/{softSkillId}")
    public ResponseEntity<List<SoftSkillRating>> getRatingBySoftSkill(@PathVariable Integer softSkillId) {
        List<SoftSkillRating> ratings = softSkillRatingRepo.findRatingsBySoftSkillId(softSkillId);
        return ResponseEntity.ok(ratings);
    }

}

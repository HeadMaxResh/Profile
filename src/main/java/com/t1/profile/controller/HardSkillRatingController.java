package com.t1.profile.controller;


import com.t1.profile.dto.HardSkillRatingDto;
import com.t1.profile.model.HardSkillRating;

import com.t1.profile.service.HardSkillRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hard-skill-rating")
public class HardSkillRatingController {

    @Autowired
    private HardSkillRatingService hardSkillRatingService;

    // Эндпоинт для добавления рейтинга
    @PostMapping("/add")
    public ResponseEntity<HardSkillRating> rateHardSkill(@RequestBody HardSkillRatingDto ratingDto) {
        try {
            HardSkillRating savedRating = hardSkillRatingService.rateHardSkill(ratingDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}

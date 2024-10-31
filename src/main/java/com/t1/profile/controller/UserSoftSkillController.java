package com.t1.profile.controller;

import com.t1.profile.dto.SoftSkillCategoryWithRatingsDto;
import com.t1.profile.dto.UserSoftSkillResponseDto;
import com.t1.profile.dto.UserSoftSkillRequestDto;
import com.t1.profile.service.UserSoftSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soft-skill-rating")
public class UserSoftSkillController {

    @Autowired
    private UserSoftSkillService userSoftSkillService;

    @Autowired
    private UserSoftSkillService softSkillService;

    @PostMapping("/add")
    public ResponseEntity<UserSoftSkillResponseDto> rateSoftSkill(@RequestBody UserSoftSkillRequestDto ratingDto) {
        UserSoftSkillResponseDto savedRating = userSoftSkillService.rateSoftSkill(ratingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @GetMapping("/soft-skill/{softSkillId}")
    public ResponseEntity<List<UserSoftSkillResponseDto>> getRatingBySoftSkill(@PathVariable Integer softSkillId) {
        List<UserSoftSkillResponseDto> ratings = userSoftSkillService.getRatingBySoftSkill(softSkillId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/user/{userId}/soft-skills-with-ratings")
    public ResponseEntity<List<SoftSkillCategoryWithRatingsDto>> getSoftSkillsWithRatingsByUser(@PathVariable Integer userId) {
        List<SoftSkillCategoryWithRatingsDto> result = userSoftSkillService.getSoftSkillsWithRatingsByUser(userId);
        return ResponseEntity.ok(result);
    }

}

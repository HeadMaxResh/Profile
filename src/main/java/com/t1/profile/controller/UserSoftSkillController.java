package com.t1.profile.controller;

import com.t1.profile.dto.SoftSkillRatingDto;
import com.t1.profile.service.UserSoftSkillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soft-skill-rating")
public class UserSoftSkillController {

    @Autowired
    private UserSoftSkillServiceImpl userSoftSkillService;

    @PostMapping("/add")
    public ResponseEntity<SoftSkillRatingDto> rateSoftSkill(@RequestBody SoftSkillRatingDto ratingDto) {
        SoftSkillRatingDto savedRating = userSoftSkillService.rateSoftSkill(ratingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @GetMapping("/soft-skill/{softSkillId}")
    public ResponseEntity<List<SoftSkillRatingDto>> getRatingBySoftSkill(@PathVariable Integer softSkillId) {
        List<SoftSkillRatingDto> ratings = userSoftSkillService.getRatingBySoftSkill(softSkillId);
        return ResponseEntity.ok(ratings);
    }

}

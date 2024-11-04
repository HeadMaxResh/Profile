package com.t1.profile.skill.soft.controller;

import com.t1.profile.skill.soft.dto.SoftSkillCategoryWithRatingsDto;
import com.t1.profile.skill.soft.dto.UserSoftSkillBatchRequestDto;
import com.t1.profile.skill.soft.dto.UserSoftSkillRequestDto;
import com.t1.profile.skill.soft.dto.UserSoftSkillResponseDto;
import com.t1.profile.skill.soft.service.UserSoftSkillService;
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

    @PostMapping("/add-multiple")
    public ResponseEntity<List<UserSoftSkillResponseDto>> rateMultipleSoftSkills(
            @RequestBody UserSoftSkillBatchRequestDto batchRequestDto
    ) {
        List<UserSoftSkillResponseDto> savedRatings = userSoftSkillService.rateMultipleSoftSkills(batchRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRatings);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserSoftSkill(@PathVariable Integer id) {
        userSoftSkillService.deleteUserSoftSkill(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-all/user/{userId}")
    public ResponseEntity<Void> deleteAllUserSoftSkills(@PathVariable Integer userId) {
        userSoftSkillService.deleteAllUserSoftSkillsByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/soft-skill/{softSkillId}")
    public ResponseEntity<List<UserSoftSkillResponseDto>> getRatingBySoftSkill(@PathVariable Integer softSkillId) {
        List<UserSoftSkillResponseDto> ratings = userSoftSkillService.getRatingBySoftSkill(softSkillId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/user/{userId}/soft-skills-with-ratings")
    public ResponseEntity<List<SoftSkillCategoryWithRatingsDto>> getSoftSkillsWithRatingsByUser(
            @PathVariable Integer userId
    ) {
        List<SoftSkillCategoryWithRatingsDto> result = userSoftSkillService.getSoftSkillsWithRatingsByUser(userId);
        return ResponseEntity.ok(result);
    }

}

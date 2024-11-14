package com.t1.profile.review_service.controller;

import com.t1.profile.review_service.dto.ReviewAssignmentDto;
import com.t1.profile.review_service.dto.ReviewCycleDto;
import com.t1.profile.review_service.mapper.ReviewCycleMapper;
import com.t1.profile.review_service.model.ReviewCycle;
import com.t1.profile.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewCycleMapper reviewCycleMapper;

    @PostMapping("/cycles")
    public ResponseEntity<ReviewCycleDto> createReviewCycle(@RequestBody ReviewCycleDto reviewCycleDto) {
        ReviewCycle reviewCycle = reviewService.createReviewCycle(reviewCycleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewCycleMapper.toDto(reviewCycle));
    }

    @PostMapping("/cycles/{id}/start")
    public ResponseEntity<Void> startReviewCycle(@PathVariable Long id) {
        reviewService.startReviewCycle(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cycles/{id}/complete")
    public ResponseEntity<Void> completeReviewCycle(@PathVariable Long id) {
        reviewService.completeReviewCycle(id);
        return ResponseEntity.ok().build();
    }

    // Назначение заданий на ревью
    @PostMapping("/cycles/{id}/assign-tasks")
    public ResponseEntity<Void> assignReviewTasks(
            @PathVariable Long id,
            @RequestBody List<ReviewAssignmentDto> assignments) {
        reviewService.assignReviewTasks(id, assignments);
        return ResponseEntity.ok().build();
    }

}


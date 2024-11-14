package com.t1.profile.review_service.controller;

import com.t1.profile.review_service.dto.FeedbackDto;
import com.t1.profile.review_service.dto.ReviewTaskDto;
import com.t1.profile.review_service.mapper.ReviewTaskMapper;
import com.t1.profile.review_service.model.ReviewTask;
import com.t1.profile.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews/review-tasks")
public class ReviewTaskController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewTaskMapper reviewTaskMapper;

    @GetMapping("/my-tasks")
    public ResponseEntity<List<ReviewTaskDto>> getMyReviewTasks(@RequestParam Integer reviewerId) {
        List<ReviewTask> tasks = reviewService.getReviewTasksForReviewer(reviewerId);
        List<ReviewTaskDto> taskDtos = tasks.stream()
                .map(reviewTaskMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }

    @PostMapping("/{taskId}/submit-feedback")
    public ResponseEntity<Void> submitFeedback(
            @PathVariable Integer taskId,
            @RequestBody FeedbackDto feedbackDto) {
        reviewService.submitFeedback(taskId, feedbackDto.getFeedback());
        return ResponseEntity.ok().build();
    }

}


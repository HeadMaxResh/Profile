package com.t1.profile.controller;

import com.t1.profile.dto.FeedbackDto;
import com.t1.profile.dto.ReviewTaskDto;
import com.t1.profile.dto.UserSummaryDto;
import com.t1.profile.model.ReviewTask;
import com.t1.profile.model.User;
import com.t1.profile.security.UserDetailsImpl;
import com.t1.profile.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/review-tasks")
public class ReviewTaskController {

    @Autowired
    private ReviewService reviewService;

    // Получение заданий на ревью для текущего пользователя
    @GetMapping("/my-tasks")
    public ResponseEntity<List<ReviewTaskDto>> getMyReviewTasks(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer reviewerId = userDetails.getId();

        List<ReviewTask> tasks = reviewService.getReviewTasksForReviewer(reviewerId);
        List<ReviewTaskDto> taskDtos = tasks.stream()
                .map(this::mapToDto)
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


    private ReviewTaskDto mapToDto(ReviewTask reviewTask) {
        ReviewTaskDto dto = new ReviewTaskDto();
        dto.setId(reviewTask.getId());
        dto.setReviewCycleId(reviewTask.getReviewCycle().getId());
        dto.setStatus(reviewTask.getStatus().name());

        // Маппинг Reviewer
        User reviewer = reviewTask.getReviewer();
        UserSummaryDto reviewerDto = new UserSummaryDto();
        reviewerDto.setId(reviewer.getId());
        reviewerDto.setFirstName(reviewer.getFirstName());
        reviewerDto.setLastName(reviewer.getLastName());
        // Добавьте другие поля при необходимости

        // Маппинг Reviewee
        User reviewee = reviewTask.getReviewee();
        UserSummaryDto revieweeDto = new UserSummaryDto();
        revieweeDto.setId(reviewee.getId());
        revieweeDto.setFirstName(reviewee.getFirstName());
        revieweeDto.setLastName(reviewee.getLastName());
        // Добавьте другие поля при необходимости

        dto.setReviewer(reviewerDto);
        dto.setReviewee(revieweeDto);

        return dto;
    }

}


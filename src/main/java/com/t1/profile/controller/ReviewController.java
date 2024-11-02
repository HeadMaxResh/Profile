package com.t1.profile.controller;

import com.t1.profile.dto.ReviewAssignmentDto;
import com.t1.profile.dto.ReviewCycleDto;
import com.t1.profile.model.ReviewCycle;
import com.t1.profile.service.ReviewService;
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

    // Создание нового цикла ревью
    @PostMapping("/cycles")
    public ResponseEntity<ReviewCycleDto> createReviewCycle(@RequestBody ReviewCycleDto reviewCycleDto) {
        ReviewCycle reviewCycle = reviewService.createReviewCycle(reviewCycleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(reviewCycle));
    }

    // Запуск цикла ревью
    @PostMapping("/cycles/{id}/start")
    public ResponseEntity<Void> startReviewCycle(@PathVariable Long id) {
        reviewService.startReviewCycle(id);
        return ResponseEntity.ok().build();
    }

    // Завершение цикла ревью
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

    // Метод для маппинга ReviewCycle в ReviewCycleDto
    private ReviewCycleDto mapToDto(ReviewCycle reviewCycle) {
        ReviewCycleDto dto = new ReviewCycleDto();
        dto.setId(reviewCycle.getId());
        dto.setName(reviewCycle.getName());
        dto.setStartDate(reviewCycle.getStartDate());
        dto.setEndDate(reviewCycle.getEndDate());
        dto.setStatus(reviewCycle.getStatus().name());
        return dto;
    }

}


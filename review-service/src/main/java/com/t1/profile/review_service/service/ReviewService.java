package com.t1.profile.review_service.service;

import com.t1.profile.review_service.dto.ReviewAssignmentDto;
import com.t1.profile.review_service.dto.ReviewCycleDto;
import com.t1.profile.review_service.dto.UserDto;
import com.t1.profile.review_service.model.ReviewCycle;
import com.t1.profile.review_service.model.ReviewTask;
import com.t1.profile.review_service.repository.ReviewCycleRepository;
import com.t1.profile.review_service.repository.ReviewTaskRepository;
import com.t1.profile.review_service.status.ReviewStatus;
import com.t1.profile.review_service.status.ReviewTaskStatus;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewCycleRepository reviewCycleRepository;

    @Autowired
    private ReviewTaskRepository reviewTaskRepository;

    @Autowired
    private UserService userService;

    public ReviewCycle createReviewCycle(ReviewCycleDto reviewCycleDto) {
        ReviewCycle reviewCycle = new ReviewCycle();
        reviewCycle.setName(reviewCycleDto.getName());
        reviewCycle.setStartDate(reviewCycleDto.getStartDate());
        reviewCycle.setEndDate(reviewCycleDto.getEndDate());
        reviewCycle.setStatus(ReviewStatus.PLANNED);

        return reviewCycleRepository.save(reviewCycle);
    }


    public void startReviewCycle(Long reviewCycleId) {
        ReviewCycle reviewCycle = reviewCycleRepository.findById(reviewCycleId)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewCycle not found"));

        reviewCycle.setStatus(ReviewStatus.IN_PROGRESS);
        reviewCycleRepository.save(reviewCycle);
    }


    public void completeReviewCycle(Long reviewCycleId) {
        ReviewCycle reviewCycle = reviewCycleRepository.findById(reviewCycleId)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewCycle not found"));

        reviewCycle.setStatus(ReviewStatus.COMPLETED);
        reviewCycleRepository.save(reviewCycle);

    }

    public void assignReviewTasks(Long reviewCycleId, List<ReviewAssignmentDto> assignments) {
        ReviewCycle reviewCycle = reviewCycleRepository.findById(reviewCycleId)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewCycle not found"));

        for (ReviewAssignmentDto assignment : assignments) {
            Integer reviewerId = assignment.getReviewerId();
            Integer revieweeId = assignment.getRevieweeId();

            UserDto reviewer = userService.getUserById(reviewerId);
                    //.orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));
            UserDto reviewee = userService.getUserById(revieweeId);
                    //.orElseThrow(() -> new ResourceNotFoundException("Reviewee not found"));

            ReviewTask reviewTask = new ReviewTask();
            reviewTask.setReviewCycle(reviewCycle);
            reviewTask.setReviewerId(reviewerId);
            reviewTask.setRevieweeId(revieweeId );
            reviewTask.setStatus(ReviewTaskStatus.PENDING);

            reviewTaskRepository.save(reviewTask);

        }
    }

    public List<ReviewTask> getReviewTasksForReviewer(Integer reviewerId) {
        return reviewTaskRepository.findByReviewerId(reviewerId);
    }

    public void submitFeedback(Integer reviewTaskId, String feedback) {
        ReviewTask reviewTask = reviewTaskRepository.findById(reviewTaskId)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewTask not found"));

        reviewTask.setFeedback(feedback);
        reviewTask.setStatus(ReviewTaskStatus.COMPLETED);
        reviewTaskRepository.save(reviewTask);
    }


    public void markTaskAsCompleted(Integer reviewerId, Integer revieweeId) {

        ReviewTask reviewTask =
                reviewTaskRepository.findByReviewerIdAndRevieweeId(reviewerId, revieweeId);

        if (reviewTask != null) {
            reviewTask.setStatus(ReviewTaskStatus.COMPLETED);
            reviewTaskRepository.save(reviewTask);
            System.out.println("Задача помечена как завершенная для reviewer ID: " +
                    reviewerId + " и reviewee ID: " + revieweeId);
        } else {
            System.err.println("Задача ревью не найдена для reviewer ID: " +
                    reviewerId + " и reviewee ID: " + revieweeId);
        }

    }

}



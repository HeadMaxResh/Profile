package com.t1.profile.service;

import com.t1.profile.ReviewStatus;
import com.t1.profile.ReviewTaskStatus;
import com.t1.profile.dto.ReviewAssignmentDto;
import com.t1.profile.dto.ReviewCycleDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.ReviewCycle;
import com.t1.profile.model.ReviewTask;
import com.t1.profile.model.User;
import com.t1.profile.repository.ReviewCycleRepository;
import com.t1.profile.repository.ReviewTaskRepository;
import com.t1.profile.repository.UserRepo;
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
    private UserRepo userRepository;

    @Autowired
    private NotificationService notificationService;

    // Создание нового цикла ревью
    public ReviewCycle createReviewCycle(ReviewCycleDto reviewCycleDto) {
        ReviewCycle reviewCycle = new ReviewCycle();
        reviewCycle.setName(reviewCycleDto.getName());
        reviewCycle.setStartDate(reviewCycleDto.getStartDate());
        reviewCycle.setEndDate(reviewCycleDto.getEndDate());
        reviewCycle.setStatus(ReviewStatus.PLANNED);

        return reviewCycleRepository.save(reviewCycle);
    }

    // Запуск цикла ревью
    public void startReviewCycle(Long reviewCycleId) {
        ReviewCycle reviewCycle = reviewCycleRepository.findById(reviewCycleId)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewCycle not found"));

        reviewCycle.setStatus(ReviewStatus.IN_PROGRESS);
        reviewCycleRepository.save(reviewCycle);

        // Отправка уведомлений
        notificationService.notifyReviewCycleStarted(reviewCycle);
    }

    // Завершение цикла ревью
    public void completeReviewCycle(Long reviewCycleId) {
        ReviewCycle reviewCycle = reviewCycleRepository.findById(reviewCycleId)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewCycle not found"));

        reviewCycle.setStatus(ReviewStatus.COMPLETED);
        reviewCycleRepository.save(reviewCycle);

        // Отправка уведомлений
        notificationService.notifyReviewCycleCompleted(reviewCycle);
    }

    // Назначение заданий на ревью
    public void assignReviewTasks(Long reviewCycleId, List<ReviewAssignmentDto> assignments) {
        ReviewCycle reviewCycle = reviewCycleRepository.findById(reviewCycleId)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewCycle not found"));

        for (ReviewAssignmentDto assignment : assignments) {
            User reviewer = userRepository.findById(assignment.getReviewerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reviewer not found"));
            User reviewee = userRepository.findById(assignment.getRevieweeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Reviewee not found"));

            ReviewTask reviewTask = new ReviewTask();
            reviewTask.setReviewCycle(reviewCycle);
            reviewTask.setReviewer(reviewer);
            reviewTask.setReviewee(reviewee);
            reviewTask.setStatus(ReviewTaskStatus.PENDING);

            reviewTaskRepository.save(reviewTask);

            // Отправка уведомления ревьюеру
            notificationService.notifyReviewerAssigned(reviewTask);
        }
    }

    // Получение заданий ревью для пользователя
    public List<ReviewTask> getReviewTasksForReviewer(Integer reviewerId) {
        return reviewTaskRepository.findByReviewerId(reviewerId);
    }

    // Сохранение отзыва
    public void submitFeedback(Integer reviewTaskId, String feedback) {
        ReviewTask reviewTask = reviewTaskRepository.findById(reviewTaskId)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewTask not found"));

        reviewTask.setFeedback(feedback);
        reviewTask.setStatus(ReviewTaskStatus.COMPLETED);
        reviewTaskRepository.save(reviewTask);
    }
}


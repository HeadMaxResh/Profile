package com.t1.profile.review_service.repository;

import com.t1.profile.review_service.model.ReviewTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewTaskRepository extends JpaRepository<ReviewTask, Integer> {

    List<ReviewTask> findByReviewerId(Integer reviewerId);
    List<ReviewTask> findByRevieweeId(Integer revieweeId);
    ReviewTask findByReviewerIdAndRevieweeId(Integer reviewerId, Integer revieweeId);

}
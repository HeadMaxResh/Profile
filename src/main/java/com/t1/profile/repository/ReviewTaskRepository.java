package com.t1.profile.repository;

import com.t1.profile.model.ReviewTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewTaskRepository extends JpaRepository<ReviewTask, Integer> { // Или Long
    List<ReviewTask> findByReviewerId(Integer reviewerId);
    List<ReviewTask> findByRevieweeId(Integer revieweeId);
}



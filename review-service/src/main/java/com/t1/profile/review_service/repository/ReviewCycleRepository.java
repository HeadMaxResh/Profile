package com.t1.profile.review_service.repository;

import com.t1.profile.review_service.model.ReviewCycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewCycleRepository extends JpaRepository<ReviewCycle, Long> {
}
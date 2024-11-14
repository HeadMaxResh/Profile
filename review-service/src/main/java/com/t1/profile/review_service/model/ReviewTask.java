package com.t1.profile.review_service.model;

import com.t1.profile.review_service.status.ReviewTaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class ReviewTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "review_cycle_id")
    private ReviewCycle reviewCycle;

    @Column(nullable = false)
    private Integer reviewerId;

    @Column(nullable = false)
    private Integer revieweeId;

    @Enumerated(EnumType.STRING)
    private ReviewTaskStatus status; // PENDING, COMPLETED

    private String feedback; // Оставленный отзыв


}

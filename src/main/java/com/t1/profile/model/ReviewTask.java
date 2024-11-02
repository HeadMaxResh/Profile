package com.t1.profile.model;

import com.t1.profile.ReviewTaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewee_id")
    private User reviewee;

    @Enumerated(EnumType.STRING)
    private ReviewTaskStatus status; // PENDING, COMPLETED

    private String feedback; // Оставленный отзыв


// Геттеры и сеттеры
}


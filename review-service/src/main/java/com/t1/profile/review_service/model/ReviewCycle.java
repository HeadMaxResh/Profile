package com.t1.profile.review_service.model;

import com.t1.profile.review_service.status.ReviewStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class ReviewCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    @OneToMany(mappedBy = "reviewCycle", cascade = CascadeType.ALL)
    private List<ReviewTask> reviewTasks = new ArrayList<>();


}

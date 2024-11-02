package com.t1.profile.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.t1.profile.*;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class ReviewCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Например, "Q3 2023 Review"

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status; // PLANNED, IN_PROGRESS, COMPLETED

    @OneToMany(mappedBy = "reviewCycle", cascade = CascadeType.ALL)
    private List<ReviewTask> reviewTasks = new ArrayList<>();


}

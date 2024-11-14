package com.t1.profile.review_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewCycleDto {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

}

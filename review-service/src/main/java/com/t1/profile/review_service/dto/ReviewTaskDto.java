package com.t1.profile.review_service.dto;

import lombok.Data;

@Data
public class ReviewTaskDto {

    private Integer id;
    private Long reviewCycleId;
    private String status;
    private Integer reviewerId;
    private Integer revieweeId;

}
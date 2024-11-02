package com.t1.profile.dto;

import lombok.Data;

@Data
public class ReviewTaskDto {
    private Integer id;
    private Long reviewCycleId;
    private UserSummaryDto reviewer;
    private UserSummaryDto reviewee;
    private String status;
}

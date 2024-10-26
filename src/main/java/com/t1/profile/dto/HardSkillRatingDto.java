package com.t1.profile.dto;

public class HardSkillRatingDto {
    private Integer skillId;
    private Integer ratedUserId;
    private Integer raterUserId;
    private Integer rating;
    private Integer indicatorId;

    // Геттеры и сеттеры
    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public Integer getRatedUserId() {
        return ratedUserId;
    }

    public void setRatedUserId(Integer ratedUserId) {
        this.ratedUserId = ratedUserId;
    }

    public Integer getRaterUserId() {
        return raterUserId;
    }

    public void setRaterUserId(Integer raterUserId) {
        this.raterUserId = raterUserId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}

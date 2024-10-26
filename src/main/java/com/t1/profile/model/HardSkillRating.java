package com.t1.profile.model;

import jakarta.persistence.*;

@Entity
public class HardSkillRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "indicator_id")
    private HardSkillIndicator indicator;

    @ManyToOne
    @JoinColumn(name = "rated_user_id")
    private User ratedUser;

    @ManyToOne
    @JoinColumn(name = "rater_user_id")
    private User raterUser;

    private Integer rating;

    // Геттеры и сеттеры для всех полей

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HardSkillIndicator getIndicator() {
        return indicator;
    }

    public void setIndicator(HardSkillIndicator indicator) {
        this.indicator = indicator;
    }

    public User getRatedUser() {
        return ratedUser;
    }

    public void setRatedUser(User ratedUser) {
        this.ratedUser = ratedUser;
    }

    public User getRaterUser() {
        return raterUser;
    }

    public void setRaterUser(User raterUser) {
        this.raterUser = raterUser;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}

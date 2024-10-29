package com.t1.profile.repository;

import com.t1.profile.model.SoftSkill;
import com.t1.profile.model.UserSoftSkill;
import com.t1.profile.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserSoftSkillRepoTest {

    @Autowired
    private UserSoftSkillRepo userSoftSkillRepo;

    @Autowired
    private SoftSkillRepo softSkillRepo;

    @Autowired
    private UserRepo userRepo;

    private SoftSkill softSkill;
    private User ratedUser;
    private User raterUser;

    @BeforeEach
    public void setUp() {
        ratedUser = new User();
        ratedUser.setEmail("rated.user@example.com");
        ratedUser = userRepo.save(ratedUser);

        raterUser = new User();
        raterUser.setEmail("rater.user@example.com");
        raterUser = userRepo.save(raterUser);

        softSkill = new SoftSkill();
        softSkill.setName("Communication");
        softSkill = softSkillRepo.save(softSkill);

        UserSoftSkill rating1 = new UserSoftSkill();
        rating1.setRatedUser(ratedUser);
        rating1.setRaterUser(raterUser);
        rating1.setSoftSkill(softSkill);
        rating1.setRating(3);
        userSoftSkillRepo.save(rating1);

        UserSoftSkill rating2 = new UserSoftSkill();
        rating2.setRatedUser(ratedUser);
        rating2.setRaterUser(raterUser);
        rating2.setSoftSkill(softSkill);
        rating2.setRating(5);
        userSoftSkillRepo.save(rating2);
    }

    @Test
    public void testFindRatingsBySoftSkillId() {
        List<UserSoftSkill> ratings = userSoftSkillRepo.findRatingsBySoftSkillId(softSkill.getId());

        assertThat(ratings).isNotEmpty();
        assertThat(ratings.size()).isEqualTo(2);
    }

    @Test
    public void testFindByRatedUserId() {
        List<UserSoftSkill> ratings = userSoftSkillRepo.findByRatedUserId(ratedUser.getId());

        assertThat(ratings).isNotEmpty();
        assertThat(ratings.size()).isEqualTo(2);
    }

    @Test
    public void testFindByRaterId() {
        List<UserSoftSkill> ratings = userSoftSkillRepo.findByRaterId(raterUser.getId());

        assertThat(ratings).isNotEmpty();
        assertThat(ratings.size()).isEqualTo(2);
    }

    @Test
    public void testFindAverageRatingByUserAndSoftSkill() {
        Double averageRating = userSoftSkillRepo.findAverageRatingByUserAndSoftSkill(ratedUser.getId(), softSkill.getId());

        assertThat(averageRating).isNotNull();
        assertThat(averageRating).isEqualTo(4.0);
    }
}

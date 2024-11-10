package com.t1.profile.skill.hard.repository;

import com.t1.profile.skill.hard.model.HardSkill;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.skill.hard.repository.HardSkillRepo;
import com.t1.profile.user.model.User;
import com.t1.profile.profession.repository.ProfessionRepo;
import com.t1.profile.user.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HardSkillRepoTest {

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProfessionRepo professionRepo;

    private User user;
    private Profession profession;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("test.user@example.com");
        user = userRepo.save(user);

        profession = new Profession();
        profession.setName("Software Developer");
        profession = professionRepo.save(profession);

        HardSkill skill1 = new HardSkill();
        skill1.setName("Java");
        skill1.setProfession(profession);
        hardSkillRepo.save(skill1);

        HardSkill skill2 = new HardSkill();
        skill2.setName("Python");
        skill2.setProfession(profession);
        hardSkillRepo.save(skill2);
    }

    @Test
    public void testFindByProfessionId() {
        List<HardSkill> skills = hardSkillRepo.findByProfessionId(profession.getId());

        assertThat(skills).isNotEmpty();
        assertThat(skills).hasSize(2);
    }

    @Test
    public void testFindByProfessionId_NotFound() {
        List<HardSkill> skills = hardSkillRepo.findByProfessionId(999);

        assertThat(skills).isEmpty();
    }
}

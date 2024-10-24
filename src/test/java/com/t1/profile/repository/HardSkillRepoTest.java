package com.t1.profile.repository;

import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
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
        user.setId(1);
        user.setEmail("test.user@example.com");
        user = userRepo.save(user);

        profession = new Profession();
        profession.setId(1);
        profession.setName("Software Developer");
        profession = professionRepo.save(profession);

        HardSkill skill1 = new HardSkill();
        skill1.setId(1);
        skill1.setName("Java");
        skill1.setUser(user);
        skill1.setProfession(profession);

        HardSkill skill2 = new HardSkill();
        skill2.setId(2);
        skill2.setName("Python");
        skill2.setUser(user);
        skill2.setProfession(profession);

        hardSkillRepo.save(skill1);
        hardSkillRepo.save(skill2);
    }

    @Test
    public void testFindByUserId() {
        List<HardSkill> skills = hardSkillRepo.findByUserId(user.getId());

        assertThat(skills).isNotEmpty();
        assertThat(skills.size()).isEqualTo(2);
    }

    @Test
    public void testFindByProfessionId() {
        List<HardSkill> skills = hardSkillRepo.findByProfessionId(profession.getId());

        assertThat(skills).isNotEmpty();
        assertThat(skills.size()).isEqualTo(2);
    }

    @Test
    public void testFindByUserId_NotFound() {
        List<HardSkill> skills = hardSkillRepo.findByUserId(999);

        assertThat(skills).isEmpty();
    }

    @Test
    public void testFindByProfessionId_NotFound() {
        List<HardSkill> skills = hardSkillRepo.findByProfessionId(999);

        assertThat(skills).isEmpty();
    }

}

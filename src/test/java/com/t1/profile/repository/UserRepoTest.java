package com.t1.profile.repository;

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
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProfessionRepo professionRepo;

    private User user;

    @BeforeEach
    public void setUp() {
        Profession profession = new Profession();
        profession.setName("Software Developer");
        profession = professionRepo.save(profession);

        user = new User();
        user.setProfession(profession);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPasswordHash("hashed_password");
        userRepo.save(user);
    }

    @Test
    public void testFindByEmail() {
        User foundUser = userRepo.findByEmail("john.doe@example.com");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    public void testFindByProfession() {
        Profession profession = user.getProfession();

        List<User> usersWithProfession = userRepo.findByProfession(profession);

        assertThat(usersWithProfession).isNotEmpty();
        assertThat(usersWithProfession.get(0).getProfession().getName()).isEqualTo("Software Developer");
    }

    @Test
    public void testFindAll() {
        List<User> allUsers = userRepo.findAll();

        assertThat(allUsers).isNotEmpty();
        assertThat(allUsers.size()).isEqualTo(1);
    }
}

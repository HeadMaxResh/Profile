package com.t1.profile.repository;

import com.t1.profile.model.Profession;
import com.t1.profile.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Используем реальную базу данных, если нужно
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProfessionRepo professionRepo;  // Добавляем ProfessionRepo

    @Test
    void testFindByProfession() {
        // Создаем и сохраняем Profession
        Profession profession = new Profession();
        profession.setName("Engineer");
        professionRepo.save(profession);  // Сохраняем Profession

        // Создаем и сохраняем User с Profession
        User user = new User();
        user.setEmail("user@example.com");
        user.setName("John Doe");
        user.setProfession(profession);  // Связываем User с Profession
        userRepo.save(user);  // Сохраняем User

        // Тестируем метод findByProfession
        List<User> users = userRepo.findByProfession(profession);
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
    }
}

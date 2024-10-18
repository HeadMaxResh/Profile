package com.t1.profile.repository;

import com.t1.profile.model.Role;
import com.t1.profile.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {

    // Метод для поиска пользователей по их роли
    @Query("SELECT u FROM User u WHERE :role MEMBER OF u.roles")
    List<User> findByRole(@Param("role") Role role);

    // Метод для поиска пользователя по email
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

}

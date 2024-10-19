package com.t1.profile.repository;

import com.t1.profile.model.Profession;
import com.t1.profile.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User, Long> { // Изменено с Integer на Long

    @Query("SELECT u FROM User u WHERE u.profession = :profession")
    List<User> findByProfession(@Param("profession") Profession profession);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
}

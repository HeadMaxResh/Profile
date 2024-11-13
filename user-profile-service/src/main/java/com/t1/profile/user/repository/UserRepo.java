package com.t1.profile.user.repository;

import com.t1.profile.Table;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM " + Table.TABLE_USER + " u WHERE u.profession = :profession")
    List<User> findByProfession(@Param("profession") Profession profession);

    @Query("SELECT u FROM " + Table.TABLE_USER + " u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    List<User> findAll();

}

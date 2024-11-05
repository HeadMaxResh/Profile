package com.t1.profile.user.repository;

import com.t1.profile.Table;
import com.t1.profile.profession.model.Profession;
import com.t1.profile.user.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends CrudRepository<User, Long> {

    @Query("SELECT u FROM " + Table.TABLE_USER + " u WHERE u.profession = :profession")
    List<User> findByProfession(@Param("profession") Profession profession);

    @Query("SELECT u FROM " + Table.TABLE_USER + " u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    List<User> findAll();

    List<User> findByUsername(String username);

    @Query(value = """
        SELECT * FROM user u
        WHERE u.username LIKE :query
           OR u.name LIKE :query
           OR u.lastname LIKE :query
           OR u.phone_number LIKE :query
           OR u.skills LIKE :query
           OR u.position LIKE :query
           OR u.area_of_responsibility LIKE :query
           OR u.messenger LIKE :query
           OR u.experience LIKE :query
       """, nativeQuery = true)
    List<User> findByQuery(@Param("query") String query);

    @Query(value = """
        SELECT u.* FROM user u
        LEFT JOIN teammate t ON t.user_id = u.id
        WHERE (u.username LIKE :query
               OR u.name LIKE :query)
        AND (t.user_id IS NULL OR t.team_id != :teamId)
       """, nativeQuery = true)
    List<User> findByQueryAndNotInTeam(@Param("query") String query, @Param("teamId") Long teamId);

    List<User> findByProfessionId(Long professionId);
}

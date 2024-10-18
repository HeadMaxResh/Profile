package com.t1.profile.repository;

import com.t1.profile.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);
}



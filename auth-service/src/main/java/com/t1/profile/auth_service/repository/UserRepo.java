package com.t1.profile.auth_service.repository;

import com.t1.profile.auth_service.model.Role;
import com.t1.profile.auth_service.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends CrudRepository<User, Integer> {

    User findByEmail(String email);

}

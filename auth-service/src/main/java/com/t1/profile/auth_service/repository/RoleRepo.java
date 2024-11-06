package com.t1.profile.auth_service.repository;

import com.t1.profile.auth_service.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Integer> {

    Role findByName(String name);

}

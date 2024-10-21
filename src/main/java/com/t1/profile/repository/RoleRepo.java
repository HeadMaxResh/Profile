package com.t1.profile.repository;

import com.t1.profile.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Integer> {

    Role findByName(String name);

}

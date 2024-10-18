package com.t1.profile.repository;

import com.t1.profile.model.Profession;
import org.springframework.data.repository.CrudRepository;

public interface ProfessionRepo extends CrudRepository<Profession, Integer> {
}

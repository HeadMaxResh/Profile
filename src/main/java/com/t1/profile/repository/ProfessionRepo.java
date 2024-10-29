package com.t1.profile.repository;

import com.t1.profile.model.Profession;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfessionRepo extends CrudRepository<Profession, Integer> {

    List<Profession> findAll();

}

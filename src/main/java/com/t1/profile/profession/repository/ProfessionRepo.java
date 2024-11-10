package com.t1.profile.profession.repository;

import com.t1.profile.profession.model.Profession;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfessionRepo extends JpaRepository<Profession, Integer> {

}

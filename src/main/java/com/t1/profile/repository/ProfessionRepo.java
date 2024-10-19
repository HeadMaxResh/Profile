package com.t1.profile.repository;

import com.t1.profile.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionRepo extends JpaRepository<Profession, Integer> {
}
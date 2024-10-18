package com.t1.profile.repository;

import com.t1.profile.enums.CompetencyType;
import com.t1.profile.model.Competency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetencyRepository extends JpaRepository<Competency, Integer> {

    List<Competency> findByType(CompetencyType type);

    List<Competency> findByParentId(Integer parentId);

}

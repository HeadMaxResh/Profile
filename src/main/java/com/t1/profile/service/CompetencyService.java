package com.t1.profile.service;

import com.t1.profile.model.Competency;
import com.t1.profile.repository.CompetencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetencyService {

    private final CompetencyRepository competencyRepository;

    @Autowired
    public CompetencyService(CompetencyRepository competencyRepository) {
        this.competencyRepository = competencyRepository;
    }

    public List<Competency> findAll() {
        return competencyRepository.findAll();
    }

    public Optional<Competency> findById(Integer id) {
        return competencyRepository.findById(id);
    }

    public Competency save(Competency competency) {
        return competencyRepository.save(competency);
    }

    public void deleteById(Integer id) {
        competencyRepository.deleteById(id);
    }

    public List<Competency> findByType(CompetencyType type) {
        return competencyRepository.findByType(type);
    }

    public List<Competency> findByParentId(Integer parentId) {
        return competencyRepository.findByParentId(parentId);
    }
}

package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.model.HardSkill;
import com.t1.profile.repository.HardSkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HardSkillServiceImpl implements HardSkillService {

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Override
    public HardSkill addHardSkill(HardSkillDto hardSkillDto) {
        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());
        return hardSkillRepo.save(hardSkill);
    }

    @Override
    public HardSkill updateHardSkill(Integer hardSkillId, HardSkillDto hardSkillDto) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        hardSkill.setName(hardSkillDto.getName());
        return hardSkillRepo.save(hardSkill);
    }

    @Override
    public void deleteHardSkill(Integer hardSkillId) {
        hardSkillRepo.deleteById(hardSkillId);
    }

    @Override
    public List<HardSkillDto> getAllHardSkills() {
        return ((List<HardSkill>) hardSkillRepo.findAll()).stream()
                .map(HardSkillDto::new)  // Используем конструктор HardSkillDto(HardSkill hardSkill)
                .collect(Collectors.toList());
    }
}

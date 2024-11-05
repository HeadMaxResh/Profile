package com.t1.profile.skill.hard.service;

import com.t1.profile.skill.hard.dto.HardSkillDto;
import com.t1.profile.skill.hard.exception.HardSkillNotFoundException;
import com.t1.profile.skill.hard.mapper.HardSkillMapper;
import com.t1.profile.skill.hard.model.HardSkill;
import com.t1.profile.skill.hard.repository.HardSkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardSkillServiceImpl implements HardSkillService {

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Autowired
    private HardSkillMapper hardSkillMapper;

    @Override
    public List<HardSkillDto> getAllHardSkills() {
        return hardSkillMapper.toDtoList(hardSkillRepo.findAll());
    }

    @Override
    public HardSkillDto addHardSkill(HardSkillDto hardSkillDto) {
        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());
        HardSkill savedHardSkill = hardSkillRepo.save(hardSkill);
        return hardSkillMapper.toDto(savedHardSkill);
    }

    @Override
    public HardSkillDto updateHardSkill(Long hardSkillId, HardSkillDto hardSkillDto) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new HardSkillNotFoundException("HardSkill not found with id " + hardSkillId));
        hardSkill.setName(hardSkillDto.getName());
        HardSkill savedHardSkill = hardSkillRepo.save(hardSkill);
        return hardSkillMapper.toDto(savedHardSkill);
    }

    @Override
    public void deleteHardSkill(Long hardSkillId) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new HardSkillNotFoundException("HardSkill not found with id " + hardSkillId));

        hardSkillRepo.delete(hardSkill);
    }

}

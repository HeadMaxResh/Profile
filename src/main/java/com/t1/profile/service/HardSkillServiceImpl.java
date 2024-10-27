package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.HardSkillMapper;
import com.t1.profile.model.HardSkill;
import com.t1.profile.repository.HardSkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HardSkillServiceImpl implements HardSkillService {

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Autowired
    private HardSkillMapper hardSkillMapper;

    @Override
    public HardSkillDto addHardSkill(HardSkillDto hardSkillDto) {
        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());
        HardSkill savedHardSkill = hardSkillRepo.save(hardSkill);
        return hardSkillMapper.toDto(savedHardSkill);
    }

    @Override
    public HardSkillDto updateHardSkill(Integer hardSkillId, HardSkillDto hardSkillDto) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));
        hardSkill.setName(hardSkillDto.getName());
        HardSkill savedHardSkill = hardSkillRepo.save(hardSkill);
        return hardSkillMapper.toDto(savedHardSkill);
    }

    @Override
    public void deleteHardSkill(Integer hardSkillId) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        hardSkillRepo.delete(hardSkill);
    }

}

package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.HardSkill;
import com.t1.profile.repository.HardSkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HardSkillServiceImpl implements HardSkillService {

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Override
    public HardSkill addHardSkill(HardSkillDto hardSkillDto) {
        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());
        hardSkill.setType(hardSkillDto.getType());
        return hardSkillRepo.save(hardSkill);
    }

    @Override
    public HardSkill updateHardSkill(Integer hardSkillId, HardSkillDto hardSkillDto) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        hardSkill.setName(hardSkillDto.getName());
        hardSkill.setType(hardSkillDto.getType());
        return hardSkillRepo.save(hardSkill);
    }

    @Override
    public void deleteHardSkill(Integer hardSkillId) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("HardSkill not found with id " + hardSkillId));

        hardSkillRepo.delete(hardSkill);
    }

}

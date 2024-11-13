package com.t1.profile.skill.hard.service;

import com.t1.profile.skill.hard.dto.HardSkillDto;
import com.t1.profile.skill.hard.exception.HardSkillNotFoundException;
import com.t1.profile.skill.hard.mapper.HardSkillMapper;
import com.t1.profile.skill.hard.model.HardSkill;
import com.t1.profile.skill.hard.model.UserHardSkill;
import com.t1.profile.skill.hard.repository.HardSkillRepo;
import com.t1.profile.skill.hard.repository.UserHardSkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HardSkillServiceImpl implements HardSkillService {

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Autowired
    private UserHardSkillRepo userHardSkillRepo;

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
    public HardSkillDto updateHardSkill(Integer hardSkillId, HardSkillDto hardSkillDto) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new HardSkillNotFoundException(hardSkillId));
        hardSkill.setName(hardSkillDto.getName());
        HardSkill savedHardSkill = hardSkillRepo.save(hardSkill);
        return hardSkillMapper.toDto(savedHardSkill);
    }

    @Transactional
    @Override
    public void deleteHardSkill(Integer hardSkillId) {
        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new HardSkillNotFoundException(hardSkillId));

        for (UserHardSkill userHardSkill : hardSkill.getUserHardSkills()) {
            userHardSkill.getUser().getUserHardSkills().remove(userHardSkill);
            userHardSkillRepo.delete(userHardSkill);
        }

        hardSkillRepo.delete(hardSkill);
    }

}

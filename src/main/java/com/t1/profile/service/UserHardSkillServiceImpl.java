package com.t1.profile.service;

import com.t1.profile.dto.HardSkillDto;
import com.t1.profile.dto.UserDto;
import com.t1.profile.dto.UserHardSkillsDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.mapper.HardSkillMapper;
import com.t1.profile.mapper.UserMapper;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.Profession;
import com.t1.profile.model.User;
import com.t1.profile.repository.HardSkillRepo;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserHardSkillServiceImpl implements UserHardSkillService {

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HardSkillMapper hardSkillMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Set<HardSkillDto> getHardSkillsByUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id " + userId));
        return user.getHardSkills().stream()
                .map(hardSkillMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto addHardSkillToUser(Integer userId, Integer hardSkillId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id " + userId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("Хардскилл не найден с id " + hardSkillId));

        user.getHardSkills().add(hardSkill);
        User savedUser = userRepo.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public void removeHardSkillFromUser(Integer userId, Integer hardSkillId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id " + userId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("Хардскилл не найден с id " + hardSkillId));

        if (user.getHardSkills().remove(hardSkill)) {
            userRepo.save(user);
        } else {
            throw new IllegalArgumentException("Хардскилл не ассоциирован с пользователем.");
        }
    }

    @Override
    public UserHardSkillsDto getUserAndProfessionHardSkills(Integer userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id " + userId));

        Profession profession = user.getProfession();
        if (profession == null) {
            throw new ResourceNotFoundException("Профессия не найдена для пользователя с id " + userId);
        }

        Integer professionId = profession.getId();

        List<HardSkill> userHardSkills = hardSkillRepo.findByUserId(userId);
        List<HardSkill> professionHardSkills = hardSkillRepo.findByProfessionId(professionId);

        List<HardSkill> commonHardSkills = new ArrayList<>();
        List<HardSkill> remainingUserHardSkills = new ArrayList<>(userHardSkills);

        for (HardSkill professionSkill : professionHardSkills) {
            if (userHardSkills.contains(professionSkill)) {

                commonHardSkills.add(professionSkill);
                remainingUserHardSkills.remove(professionSkill);

            }
        }
        return new UserHardSkillsDto(commonHardSkills, remainingUserHardSkills);
    }

    @Override
    public UserHardSkillsDto getUserAndProfessionHardSkills(Integer userId, Integer professionId) {

        List<HardSkill> userHardSkills = hardSkillRepo.findByUserId(userId);

        List<HardSkill> professionHardSkills = hardSkillRepo.findByProfessionId(professionId);

        List<HardSkill> commonHardSkills = new ArrayList<>();
        List<HardSkill> remainingUserHardSkills = new ArrayList<>(userHardSkills);

        for (HardSkill professionSkill : professionHardSkills) {
            if (userHardSkills.contains(professionSkill)) {

                commonHardSkills.add(professionSkill);
                remainingUserHardSkills.remove(professionSkill);

            }
        }
        return new UserHardSkillsDto(commonHardSkills, remainingUserHardSkills);
    }

}

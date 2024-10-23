package com.t1.profile.service;

import com.t1.profile.dto.UserDto;
import com.t1.profile.dto.UserHardSkillsDto;
import com.t1.profile.exeption.ResourceNotFoundException;
import com.t1.profile.model.HardSkill;
import com.t1.profile.model.User;
import com.t1.profile.repository.HardSkillRepo;
import com.t1.profile.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserHardSkillServiceImpl implements UserHardSkillService {

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto addHardSkillToUser(Integer userId, Integer hardSkillId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id " + userId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("Хардскилл не найден с id " + hardSkillId));

        user.getHardSkills().add(hardSkill);
        return new UserDto(userRepo.save(user));
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

        List<HardSkill> userHardSkills = hardSkillRepo.findByUserId(userId);

        Integer professionId = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id " + userId))
                .getProfession().getId();

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

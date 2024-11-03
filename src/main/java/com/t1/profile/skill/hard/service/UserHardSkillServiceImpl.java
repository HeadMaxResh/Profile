package com.t1.profile.skill.hard.service;

import com.t1.profile.profession.model.Profession;
import com.t1.profile.profession.repository.ProfessionRepo;
import com.t1.profile.skill.hard.dto.UserHardSkillDto;
import com.t1.profile.skill.hard.dto.UserHardSkillsCategorizedDto;
import com.t1.profile.skill.hard.exception.HardSkillNotFoundException;
import com.t1.profile.skill.hard.exception.UserHardSkillAssociationAlreadyExistException;
import com.t1.profile.skill.hard.exception.UserHardSkillAssociationNotFoundException;
import com.t1.profile.skill.hard.exception.UserHardSkillNotFoundException;
import com.t1.profile.skill.hard.mapper.UserHardSkillMapper;
import com.t1.profile.skill.hard.model.HardSkill;
import com.t1.profile.skill.hard.model.UserHardSkill;
import com.t1.profile.skill.hard.repository.HardSkillRepo;
import com.t1.profile.skill.hard.repository.UserHardSkillRepo;
import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.exception.UserNotFoundException;
import com.t1.profile.user.mapper.UserMapper;
import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserHardSkillServiceImpl implements UserHardSkillService {

    @Autowired
    private HardSkillRepo hardSkillRepo;

    @Autowired
    private UserHardSkillRepo userHardSkillRepo;

    @Autowired
    private ProfessionRepo professionRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserHardSkillMapper userHardSkillMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Set<UserHardSkillDto> getHardSkillsByUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден с id " + userId));
        return user.getUserHardSkills().stream()
                .map(userHardSkillMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto addHardSkillToUser(Integer userId, Integer hardSkillId, Integer rating) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден с id " + userId));

        HardSkill hardSkill = hardSkillRepo.findById(hardSkillId)
                .orElseThrow(() -> new HardSkillNotFoundException("Хардскилл не найден с id " + hardSkillId));

        List<UserHardSkill> existingUserHardSkills = userHardSkillRepo.findByUserId(userId);
        for (UserHardSkill uhs : existingUserHardSkills) {
            if (uhs.getHardSkill().getId().equals(hardSkillId)) {
                throw new UserHardSkillAssociationAlreadyExistException("Хардскилл уже ассоциирован с пользователем.");
            }
        }
        UserHardSkill userHardSkill = new UserHardSkill();
        userHardSkill.setUser(user);
        userHardSkill.setHardSkill(hardSkill);
        userHardSkill.setRating(rating);

        userHardSkillRepo.save(userHardSkill);
        return userMapper.toDto(user);
    }


    @Override
    @Transactional
    public UserHardSkillDto updateHardSkillRating(Integer userId, Integer hardSkillId, Integer newRating) {
        UserHardSkill userHardSkill = userHardSkillRepo.findByUserId(userId).stream()
                .filter(uhs -> uhs.getHardSkill().getId().equals(hardSkillId))
                .findFirst()
                .orElseThrow(() -> new UserHardSkillNotFoundException("Хардскилл не ассоциирован с пользователем."));

        userHardSkill.setRating(newRating);
        userHardSkillRepo.save(userHardSkill);

        return userHardSkillMapper.toDto(userHardSkill);
    }

    @Override
    public UserHardSkillDto updateHardSkillRating(Integer userHardSkillId, Integer newRating) {
        UserHardSkill userHardSkill = userHardSkillRepo.findById(userHardSkillId)
                .orElseThrow(() -> new UserHardSkillNotFoundException("Хардскилл не найден с id " + userHardSkillId));

        userHardSkill.setRating(newRating);
        userHardSkillRepo.save(userHardSkill);

        return userHardSkillMapper.toDto(userHardSkill);
    }

    @Override
    public void removeHardSkillFromUser(Integer userId, Integer hardSkillId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден с id " + userId));

        UserHardSkill userHardSkill = userHardSkillRepo.findByUserId(userId).stream()
                .filter(uhs -> uhs.getHardSkill().getId().equals(hardSkillId))
                .findFirst()
                .orElseThrow(()
                        -> new UserHardSkillAssociationNotFoundException("Хардскилл не ассоциирован с пользователем."));

        userHardSkillRepo.delete(userHardSkill);
        user.getUserHardSkills().remove(userHardSkill);
    }

    @Override
    public void removeHardSkillFromUser(Integer userHardSkillId) {
        UserHardSkill userHardSkill = userHardSkillRepo.findById(userHardSkillId)
                .orElseThrow(() -> new UserHardSkillNotFoundException("Хардскилл не найден с id " + userHardSkillId));

        userHardSkillRepo.delete(userHardSkill);

        User user = userRepo.findById(userHardSkill.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException(
                        "Пользователь не найден с id " + userHardSkill.getUser().getId())
                );

        user.getUserHardSkills().remove(userHardSkill);
    }

    @Override
    public UserHardSkillsCategorizedDto getUserAndProfessionHardSkills(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден с id " + userId));

        Profession profession = user.getProfession();
        if (profession == null) {
            throw new UserNotFoundException("Профессия не найдена для пользователя с id " + userId);
        }

        Integer professionId = profession.getId();

        List<UserHardSkill> userHardSkills = userHardSkillRepo.findByUserId(userId);
        List<HardSkill> professionHardSkills = hardSkillRepo.findByProfessionId(professionId);

        Set<Integer> professionSkillIds = professionHardSkills.stream()
                .map(HardSkill::getId)
                .collect(Collectors.toSet());

        List<UserHardSkill> commonHardSkills = new ArrayList<>();
        List<UserHardSkill> remainingUserHardSkills = new ArrayList<>(userHardSkills);

        for (UserHardSkill userHardSkill : userHardSkills) {
            if (professionSkillIds.contains(userHardSkill.getHardSkill().getId())) {
                commonHardSkills.add(userHardSkill);
                remainingUserHardSkills.remove(userHardSkill);
            }
        }

        var commonHardSkillsDto = userHardSkillMapper.toDtoList(commonHardSkills);
        var remainingUserHardSkillsDto = userHardSkillMapper.toDtoList(remainingUserHardSkills);

        return new UserHardSkillsCategorizedDto(commonHardSkillsDto, remainingUserHardSkillsDto);
    }

    @Override
    public UserHardSkillsCategorizedDto getUserAndProfessionHardSkills(Integer userId, Integer professionId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден с id " + userId));

        Profession profession = professionRepo.findById(professionId)
                .orElseThrow(()
                        -> new UserNotFoundException("Профессия не найдена для пользователя с id " + userId));

        List<UserHardSkill> userHardSkills = userHardSkillRepo.findByUserId(userId);
        List<HardSkill> professionHardSkills = hardSkillRepo.findByProfessionId(professionId);

        Set<Integer> professionSkillIds = professionHardSkills.stream()
                .map(HardSkill::getId)
                .collect(Collectors.toSet());

        List<UserHardSkill> commonHardSkills = new ArrayList<>();
        List<UserHardSkill> remainingUserHardSkills = new ArrayList<>(userHardSkills);

        for (UserHardSkill userHardSkill : userHardSkills) {
            if (professionSkillIds.contains(userHardSkill.getHardSkill().getId())) {
                commonHardSkills.add(userHardSkill);
                remainingUserHardSkills.remove(userHardSkill);
            }
        }

        var commonHardSkillsDto = userHardSkillMapper.toDtoList(commonHardSkills);
        var remainingUserHardSkillsDto = userHardSkillMapper.toDtoList(remainingUserHardSkills);

        return new UserHardSkillsCategorizedDto(commonHardSkillsDto, remainingUserHardSkillsDto);
    }

}

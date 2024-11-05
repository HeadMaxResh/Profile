package com.t1.profile.user.service;

import com.t1.profile.profession.model.Profession;
import com.t1.profile.user.dto.UserDto;
import com.t1.profile.user.exception.EntityNotFoundByIdException;
import com.t1.profile.user.mapper.UserMapper;
import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.UserRepo;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private UserMapper userMapper;

    @Override
    public UserDto findByEmail(String email) {
        User user =  userRepo.findByEmail(email);
        return user != null ? userMapper.toDto(user) : null;
    }

    @Override
    public List<UserDto> findAll() {
        return userRepo.findAll().stream().map(userMapper::toDto).toList();
    }

    @Override
    public List<UserDto> findByProfessionId(Long professionId) {
        List<User> users = userRepo.findByProfessionId(professionId);
        return userMapper.toDtoList(users);
    }

    @Override
    @Transactional
    public UserDto findById(Long id) {
        return userRepo.findById(id).map(userMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundByIdException(User.class, id));
    }

    @Override
    public UserDto create(UserDto userDto) {
        User entity = userMapper.toEntity(userDto);
        entity = userRepo.save(entity);
        System.out.println(entity.getId());
        return userMapper.toDto(entity);
    }

    @Override
    public UserDto update(Long id, UserDto user) {
        User entity = userRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundByIdException(User.class, id));
        User updatedUser = userMapper.toEntity(user);
        updatedUser.setId(id);
        updatedUser = userRepo.save(updatedUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public UserDto partialUpdate(Long id, UserDto userDto) {
        User user = userRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundByIdException(User.class, id));
        User updatedUser = userMapper.partialUpdate(userDto, user);
        updatedUser = userRepo.save(updatedUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new EntityNotFoundByIdException(User.class, id);
        }
        userRepo.deleteById(id);
    }

    @Override
    public List<UserDto> findByUsername(String username) {
        return userRepo.findByUsername(username).stream().map(userMapper::toDto).toList();
    }

    @Override
    public List<UserDto> findByQuery(String query) {
        List<User> users = userRepo.findByQuery(query);
        return userMapper.toDtoList(users);
    }

    @Override
    public List<UserDto> findByQueryAndNoTeamId(String query, Long noTeamId) {
        return userRepo.findByQueryAndNotInTeam(query, noTeamId).stream().map(userMapper::toDto)
            .toList();
    }

}

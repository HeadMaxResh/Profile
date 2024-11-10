package com.t1.profile.user.service;

import com.t1.profile.user.dto.UserDto;
import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Integer id);

    UserDto create(UserDto userDto);

    UserDto update(Integer id, UserDto userDto);

    UserDto partialUpdate(Integer id, UserDto userDto);

    void delete(Integer id);

    UserDto findByEmail(String email);

    List<UserDto> findByProfessionId(Integer professionId);

    List<UserDto> findByUsername(String username);

    List<UserDto> findByQuery(String query);

    List<UserDto> findByQueryAndNoTeamId(String query, Integer noTeamId);

}

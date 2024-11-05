package com.t1.profile.user.service;

import com.t1.profile.user.dto.UserDto;
import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto create(UserDto userDto);

    UserDto update(Long id, UserDto userDto);

    UserDto partialUpdate(Long id, UserDto userDto);

    void delete(Long id);

    UserDto findByEmail(String email);

    List<UserDto> findByProfessionId(Long professionId);

    List<UserDto> findByUsername(String username);

    List<UserDto> findByQuery(String query);

    List<UserDto> findByQueryAndNoTeamId(String query, Long noTeamId);

}

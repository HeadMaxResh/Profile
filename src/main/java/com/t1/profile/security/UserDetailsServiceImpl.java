package com.t1.profile.security;

import com.t1.profile.model.User;
import com.t1.profile.model.Role;
import com.t1.profile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Проверяем, загружаются ли роли
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            System.out.println("Роли пользователя не загружены!");
        } else {
            System.out.println("Роли пользователя: " + user.getRoles());
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        // Логируем авторитеты пользователя
        System.out.println("Авторитеты пользователя: " + authorities);

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                authorities
        );
    }
}

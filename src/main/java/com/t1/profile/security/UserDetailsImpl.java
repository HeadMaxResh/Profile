package com.t1.profile.security;

import com.t1.profile.model.Role;
import com.t1.profile.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // Конструктор
    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.username = user.getEmail(); // Используем email в качестве имени пользователя
        this.password = user.getPassword();
        this.authorities = user.getRoles().stream()
                .map(role -> (GrantedAuthority) () -> role.getName())
                .collect(Collectors.toList());
    }

    // Реализация методов интерфейса UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Остальные методы

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Эти методы можно настроить по необходимости
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

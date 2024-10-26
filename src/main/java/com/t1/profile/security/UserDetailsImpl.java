package com.t1.profile.security;

import com.t1.profile.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class UserDetailsImpl implements UserDetails {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email; // Добавляем поле email
    private String password;

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.email = user.getEmail(); // Инициализируем email
        this.password = user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return email; // Возвращаем email вместо имени и фамилии
    }

    @Override
    public String getPassword() {
        return password; // Убедитесь, что метод возвращает пароль
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Возвращаем пустую коллекцию вместо null
    }

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

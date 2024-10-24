package com.t1.profile.security;

import com.t1.profile.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class UserDetailsImpl implements UserDetails {

    private Integer id;
    private String firstname;
    private String lastname;
    private String password;

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.password = user.getPasswordHash();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return firstname + " " + lastname;
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

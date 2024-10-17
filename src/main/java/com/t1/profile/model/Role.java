package com.t1.profile.model;

import jakarta.persistence.*;
import lombok.Data;
import com.t1.profile.model.User;

import java.util.Set;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name; // Например, 'ROLE_USER', 'ROLE_ADMIN'

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}

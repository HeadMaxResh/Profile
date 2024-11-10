package com.t1.profile.user.repository;

import com.t1.profile.RoleType;
import com.t1.profile.user.model.Role;
import com.t1.profile.user.repository.RoleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepoTest {

    @Autowired
    private RoleRepo roleRepo;

    private Role adminRole;

    @BeforeEach
    public void setUp() {
        adminRole = new Role();
        adminRole.setName(RoleType.ADMIN);
        roleRepo.save(adminRole);
    }

    @Test
    public void testFindByName() {
        Role foundRole = roleRepo.findByName(RoleType.ADMIN);

        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getName()).isEqualTo(RoleType.ADMIN);
    }

    @Test
    public void testFindByName_NotFound() {
        Role foundRole = roleRepo.findByName(RoleType.USER);

        assertThat(foundRole).isNull();
    }

}

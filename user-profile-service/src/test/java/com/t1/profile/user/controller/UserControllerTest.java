package com.t1.profile.user.controller;

import com.t1.profile.user.model.User;
import com.t1.profile.user.repository.UserRepo;
import com.t1.profile.user.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepo userRepo;

    @Test
    public void testRegisterUser() throws Exception {
        User user = new User();
        user.setEmail("newuser@example.com");
        user.setFirstName("newuser");
        user.setPasswordHash("password");

        when(userRepo.save(user)).thenReturn(user);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"newuser@example.com\",\"username\":\"newuser\",\"password\":\"password\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("testuser@example.com");
        user.setFirstName("testuser");

        when(userRepo.findById(1)).thenReturn(java.util.Optional.of(user));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("testuser@example.com"))
                .andExpect(jsonPath("$.username").value("testuser"));
    }
}

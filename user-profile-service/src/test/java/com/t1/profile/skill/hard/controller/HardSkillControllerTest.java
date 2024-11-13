package com.t1.profile.skill.hard.controller;

import com.t1.profile.skill.hard.dto.HardSkillDto;
import com.t1.profile.skill.hard.model.HardSkill;
import com.t1.profile.skill.hard.repository.HardSkillRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HardSkillControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HardSkillRepo hardSkillRepo;

    @InjectMocks
    private HardSkillController hardSkillController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(hardSkillController).build();
    }

    @Test
    public void testAddHardSkill() throws Exception {
        HardSkillDto hardSkillDto = new HardSkillDto();
        hardSkillDto.setName("Java");
        //hardSkillDto.setType(HardSkillType.valueOf("PRIMARY"));

        HardSkill hardSkill = new HardSkill();
        hardSkill.setName(hardSkillDto.getName());
        //hardSkill.setType(hardSkillDto.getType());

        when(hardSkillRepo.save(any(HardSkill.class))).thenReturn(hardSkill);

        mockMvc.perform(post("/hard-skill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Java\",\"type\":\"PRIMARY\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Java"))
                .andExpect(jsonPath("$.type").value("PRIMARY"));

        verify(hardSkillRepo, times(1)).save(any(HardSkill.class));
    }
}

package com.t1.profile.dto;

import com.t1.profile.model.HardSkill;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HardSkillDto {

    private Integer id;
    private String name;

    // Конструктор для преобразования HardSkill в HardSkillDto
    public HardSkillDto(HardSkill hardSkill) {
        this.id = hardSkill.getId();
        this.name = hardSkill.getName();
        // Добавьте дополнительные поля при необходимости
    }
}

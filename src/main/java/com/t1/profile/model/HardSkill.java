package com.t1.profile.model;
import com.t1.profile.enums.HardSkillType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class HardSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private HardSkillType type;

    // Связь с пользователем (User)
    @ManyToOne
    @JoinColumn(name = "user_id")  // Поле для связи с User
    private User user;

    // Связь с профессией (Profession)
    @ManyToOne
    @JoinColumn(name = "profession_id")  // Поле для связи с Profession
    private Profession profession;
}

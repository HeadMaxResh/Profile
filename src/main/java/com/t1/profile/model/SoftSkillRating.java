package com.t1.profile.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class SoftSkillRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "soft_skill_id")
    private SoftSkill softSkill;

    @ManyToOne
    @JoinColumn(name = "rated_user_id")
    private User ratedUser;

    @ManyToOne
    @JoinColumn(name = "rater_user_id")
    private User raterUser;

    private Integer rating;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null)) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer()
                .getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SoftSkillRating softSkillRating = (SoftSkillRating) o;
        return getId() != null && Objects.equals(getId(), softSkillRating.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer()
                .getPersistentClass()
                .hashCode()
                : getClass().hashCode();
    }

}

package com.t1.profile.model;

import com.t1.profile.Table;
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
public class SoftSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer level;

    @ManyToOne
    @JoinColumn(name = Table.USER_ID)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SoftSkillCategory category;

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
        SoftSkill softSkill = (SoftSkill) o;
        return getId() != null && Objects.equals(getId(), softSkill.getId());
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

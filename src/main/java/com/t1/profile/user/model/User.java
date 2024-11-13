package com.t1.profile.user.model;

import com.t1.profile.Table;
import com.t1.profile.skill.hard.model.UserHardSkill;
import com.t1.profile.profession.model.Profession;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = Table.TABLE_USER)
@AllArgsConstructor
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

// Личная информация
@Column(nullable = false)
private String firstName;
private String lastName;
private LocalDate birthDate;
//    private Gender gender;
private String username;

// Контактная информация
@Column(unique = true)
private String email;
@Column(length = 11)
private String phoneNumber;
private String city;

// Аутентификация
private String passwordHash;
@Enumerated(EnumType.STRING)
private Set<Role> roles;

// Описание профиля
@Column(length = 2048)
private String bio;
private String messengerContact;
@Column(length = 512)
private String picture;

    // Настройки профиля
    //private boolean isVisibility = true;
        
    // Метаданные
    @CreationTimestamp
//    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
//    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserHardSkill> userHardSkills = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "profession_id")
    private Profession profession;

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
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
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

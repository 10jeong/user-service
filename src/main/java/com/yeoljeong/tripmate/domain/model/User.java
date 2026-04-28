package com.yeoljeong.tripmate.domain.model;

import com.yeoljeong.tripmate.domain.BaseAuditEntity;
import com.yeoljeong.tripmate.domain.enums.Gender;
import com.yeoljeong.tripmate.domain.enums.UserRole;
import com.yeoljeong.tripmate.domain.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus roleStatus = UserStatus.ACTIVE;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String email, String name, String password, String gender, LocalDate birthDate,
        String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.gender = Gender.valueOf(gender);
        this.birthDate = birthDate;
        this.role = UserRole.valueOf(role);
    }

    public static User create(String email, String name, String password, String gender,
        LocalDate birthDate, String role) {
        return User.builder()
            .email(email)
            .name(name)
            .password(password)
            .gender(gender)
            .birthDate(birthDate)
            .role(role)
            .build();
    }
}

package com.pickax.status.page.server.domain.model;

import com.pickax.status.page.server.domain.enumclass.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "signup_at", nullable = false)
    private LocalDateTime signupDate;

    @Column(name = "withdrawal_at")
    private LocalDateTime withdrawalDate;

    private User(String email, String password, UserStatus status, LocalDateTime signupDate) {
        this.email = email;
        this.password = password;
        this.status = status;
        this.signupDate = signupDate;
    }

    public static User create(String email, String encodedPassword) {
        return new User(email, encodedPassword, UserStatus.JOIN, LocalDateTime.now());
    }

    public void delete() {
        email = "";
        password = "";
        status = UserStatus.WITHDRAWAL;
        withdrawalDate = LocalDateTime.now();
    }
}

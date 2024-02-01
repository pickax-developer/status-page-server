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

    @Column(name = "sign_up_at", nullable = false)
    private LocalDateTime signUpDate;

    @Column(name = "withdrawal_at")
    private LocalDateTime withdrawalDate;

    public void delete() {
        email = "";
        password = "";
        status = UserStatus.WITHDRAWAL;
        withdrawalDate = LocalDateTime.now();
    }
}

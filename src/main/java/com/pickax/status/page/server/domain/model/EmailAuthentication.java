package com.pickax.status.page.server.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_authentications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthentication {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "code", length = 50, nullable = false)
	private String code;

	@Column(name = "expiration_at", nullable = false)
	private LocalDateTime expirationDate;

	private EmailAuthentication(String email, String code, LocalDateTime expirationDate) {
		this.email = email;
		this.code = code;
		this.expirationDate = expirationDate;
	}

	public static EmailAuthentication create(String email, String code, LocalDateTime expirationDate) {
		return new EmailAuthentication(email, code, expirationDate);
	}

	public boolean verify(String code) {
		return this.code.equals(code);
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expirationDate);
	}
}

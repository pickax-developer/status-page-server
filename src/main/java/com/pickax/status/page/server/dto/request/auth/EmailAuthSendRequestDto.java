package com.pickax.status.page.server.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailAuthSendRequestDto {
    @Email
    @NotBlank
    private String email;

    public EmailAuthSendRequestDto(String email) {
        this.email = email;
    }
}

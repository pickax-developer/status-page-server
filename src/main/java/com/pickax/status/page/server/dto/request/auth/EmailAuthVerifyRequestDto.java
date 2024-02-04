package com.pickax.status.page.server.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailAuthVerifyRequestDto {
    @NotBlank
    private String email;

    @NotBlank
    private String code;

    public EmailAuthVerifyRequestDto(String email, String code) {
        this.email = email;
        this.code = code;
    }
}

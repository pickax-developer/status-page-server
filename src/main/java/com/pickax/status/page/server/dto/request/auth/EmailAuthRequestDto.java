package com.pickax.status.page.server.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EmailAuthRequestDto {
    @Email
    @NotBlank
    private String email;
}

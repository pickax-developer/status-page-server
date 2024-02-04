package com.pickax.status.page.server.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class SignupRequestDto {
    @Email
    @NotBlank(message = "email 은(는) 공백일 수 없습니다.")
    private String email;

    @NotBlank(message = "password 은(는) 공백일 수 없습니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!#$%&?])[A-Za-z\\d!#$%&?]{8,16}$")
    private String password;

    @NotBlank(message = "code 은(는) 공백일 수 없습니다.")
    private String code;

    public SignupRequestDto(String email, String password, String code) {
        this.email = email;
        this.password = password;
        this.code = code;
    }
}
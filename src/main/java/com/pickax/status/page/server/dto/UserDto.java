package com.pickax.status.page.server.dto;

import com.pickax.status.page.server.domain.enumclass.UserStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {

    private String email;

    private String password;

    private UserStatus status;

    @QueryProjection
    public UserDto(String email, String password, UserStatus status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }
}

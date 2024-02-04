package com.pickax.status.page.server.security.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class AccessTokenResponseDto {

    private final String accessToken;

    private final Date accessTokenExpiresIn;

}

package com.pickax.status.page.server.dto.reseponse.site;

import lombok.Getter;

@Getter
public class SiteSecretKeyResponseDto {
	private String secretKey;

	public SiteSecretKeyResponseDto(String secretKey) {
		this.secretKey = secretKey;
	}

	public static SiteSecretKeyResponseDto from(String secretKey) {
		return new SiteSecretKeyResponseDto(secretKey);
	}
}

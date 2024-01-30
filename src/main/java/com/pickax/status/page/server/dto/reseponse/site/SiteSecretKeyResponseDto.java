package com.pickax.status.page.server.dto.reseponse.site;

public record SiteSecretKeyResponseDto(
	String secretKey
){
	public static SiteSecretKeyResponseDto from(String secretKey) {
		return new SiteSecretKeyResponseDto(secretKey);
	}
}

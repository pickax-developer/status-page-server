package com.pickax.status.page.server.dto.reseponse;

import lombok.Getter;

@Getter
public class SiteResponseDto {
	private String metaTag;

	private SiteResponseDto(String metaTag) {
		this.metaTag = metaTag;
	}

	public static SiteResponseDto from(String metaTag) {
		return new SiteResponseDto(metaTag);
	}
}

package com.pickax.status.page.server.dto.reseponse;

import lombok.Getter;

@Getter
public class SiteResponseDto {
	private Long id;
	private String metaTag;

	private SiteResponseDto(Long id, String metaTag) {
		this.id = id;
		this.metaTag = metaTag;
	}

	public static SiteResponseDto from(Long id, String metaTag) {
		return new SiteResponseDto(id, metaTag);
	}
}

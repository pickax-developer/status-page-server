package com.pickax.status.page.server.dto.reseponse.site;

import lombok.Getter;

@Getter
public class SiteCreateResponseDto {
	private Long id;
	private String metaTag;

	private SiteCreateResponseDto(Long id, String metaTag) {
		this.id = id;
		this.metaTag = metaTag;
	}

	public static SiteCreateResponseDto from(Long id, String metaTag) {
		return new SiteCreateResponseDto(id, metaTag);
	}
}

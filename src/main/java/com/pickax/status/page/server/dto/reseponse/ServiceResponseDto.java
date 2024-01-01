package com.pickax.status.page.server.dto.reseponse;

import lombok.Getter;

@Getter
public class ServiceResponseDto {
	private String metaTag;

	private ServiceResponseDto(String metaTag) {
		this.metaTag = metaTag;
	}

	public static ServiceResponseDto from(String metaTag) {
		return new ServiceResponseDto(metaTag);
	}
}

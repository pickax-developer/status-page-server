package com.pickax.status.page.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SiteCreateRequestDto {

	@NotBlank
	private String name;

	private String description;

	@NotBlank
	private String url;

	private SiteCreateRequestDto(String name, String description, String url) {
		this.name = name;
		this.description = description;
		this.url = url;
	}

	public static SiteCreateRequestDto of(String name, String description, String url) {
		return new SiteCreateRequestDto(name, description, url);
	}
}

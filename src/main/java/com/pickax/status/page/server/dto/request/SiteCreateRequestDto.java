package com.pickax.status.page.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record SiteCreateRequestDto(
	@NotBlank String name,

	String description,

	@NotBlank String url

) {
	public static SiteCreateRequestDto of(String name, String description, String url) {
		return new SiteCreateRequestDto(name, description, url);
	}
}

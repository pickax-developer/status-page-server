package com.pickax.status.page.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ServiceRequestDto {

	@NotBlank
	private String name;

	private String description;

	@NotBlank
	private String url;

	private ServiceRequestDto(String name, String description, String url) {
		this.name = name;
		this.description = description;
		this.url = url;
	}

	public static ServiceRequestDto of(String name, String description, String url) {
		return new ServiceRequestDto(name, description, url);
	}
}

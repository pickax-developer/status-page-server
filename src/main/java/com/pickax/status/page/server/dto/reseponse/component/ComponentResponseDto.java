package com.pickax.status.page.server.dto.reseponse.component;

import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ComponentResponseDto {
	private Long id;
	private String name;
	private String description;
	private ComponentStatus status;
	private Long frequency;
	private Boolean isActive;

	@QueryProjection
	public ComponentResponseDto(
		Long id, String name, String description, ComponentStatus status, Long frequency, Boolean isActive
	) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.frequency = frequency;
		this.isActive = isActive;
	}
}

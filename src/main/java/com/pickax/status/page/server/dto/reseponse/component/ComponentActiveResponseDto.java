package com.pickax.status.page.server.dto.reseponse.component;

import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ComponentActiveResponseDto {
	private Long id;
	private String name;
	private String description;
	private ComponentStatus status;
	private LocalDateTime lastUpdatedDate;

	@QueryProjection
	public ComponentActiveResponseDto(
		Long id, String name, String description, ComponentStatus status, LocalDateTime lastUpdatedDate
	) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.lastUpdatedDate = lastUpdatedDate;
	}
}

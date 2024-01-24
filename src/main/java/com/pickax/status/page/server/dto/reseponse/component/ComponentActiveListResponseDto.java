package com.pickax.status.page.server.dto.reseponse.component;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ComponentActiveListResponseDto {

	private List<ComponentActiveResponseDto> componentActiveResponseDtoList;

	private LocalDateTime lastUpdatedDate;

	private ComponentActiveListResponseDto(List<ComponentActiveResponseDto> componentActiveResponseDtoList, LocalDateTime lastUpdatedDate) {
		this.componentActiveResponseDtoList = componentActiveResponseDtoList;
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public static ComponentActiveListResponseDto of(List<ComponentActiveResponseDto> componentActiveResponses, LocalDateTime lastUpdatedDate) {
		return new ComponentActiveListResponseDto(componentActiveResponses, lastUpdatedDate);
	}
}

package com.pickax.status.page.server.dto.reseponse.component;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ComponentListResponseDto {
	private List<ComponentResponseDto> componentResponseDtoList;

	public ComponentListResponseDto(List<ComponentResponseDto> componentResponseDtoList) {
		this.componentResponseDtoList = componentResponseDtoList;
	}
}

package com.pickax.status.page.server.dto.reseponse.component;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ComponentActiveListResponseDto {

	private List<ComponentActiveResponseDto> componentActiveResponseDtoList;

	public ComponentActiveListResponseDto(List<ComponentActiveResponseDto> componentActiveResponseDtoList) {
		this.componentActiveResponseDtoList = componentActiveResponseDtoList;
	}
}

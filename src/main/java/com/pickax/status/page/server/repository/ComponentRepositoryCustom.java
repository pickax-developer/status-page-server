package com.pickax.status.page.server.repository;

import java.util.List;

import com.pickax.status.page.server.dto.reseponse.component.ComponentActiveResponseDto;
import com.pickax.status.page.server.dto.reseponse.component.ComponentResponseDto;

public interface ComponentRepositoryCustom {
	List<ComponentActiveResponseDto> getActiveComponents(Long siteId, boolean isActive);

	List<ComponentResponseDto> getComponents(Long siteId);
}

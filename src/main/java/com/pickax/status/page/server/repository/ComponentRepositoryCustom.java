package com.pickax.status.page.server.repository;

import java.util.List;

import com.pickax.status.page.server.dto.reseponse.ComponentResponseDto;

public interface ComponentRepositoryCustom {
	List<ComponentResponseDto> getComponents(Long siteId, boolean isActive);
}

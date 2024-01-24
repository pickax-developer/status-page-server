package com.pickax.status.page.server.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.pickax.status.page.server.domain.model.Component;
import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import com.pickax.status.page.server.dto.reseponse.component.ComponentActiveResponseDto;
import com.pickax.status.page.server.dto.reseponse.component.ComponentResponseDto;

public interface ComponentRepositoryCustom {
	List<ComponentActiveResponseDto> getComponentActiveResponses(Long siteId);

	List<ComponentResponseDto> getComponentResponses(Long siteId);

	void updateComponentStatus(Long componentId, ComponentStatus componentStatus, LocalDateTime lastUpdatedDate);

	Optional<Component> getComponent(Long id);
}

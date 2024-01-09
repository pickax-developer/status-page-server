package com.pickax.status.page.server.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import com.pickax.status.page.server.domain.model.Site;
import com.pickax.status.page.server.dto.reseponse.ComponentResponseDto;
import com.pickax.status.page.server.repository.ComponentRepository;
import com.pickax.status.page.server.repository.SiteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComponentService {
	private final ComponentRepository componentRepository;
	private final SiteRepository siteRepository;

	public List<ComponentResponseDto> getComponents(Long siteId) {
		siteRepository.findById(siteId).orElseThrow(EntityNotFoundException::new);
		return componentRepository.getComponents(siteId);
	}
}

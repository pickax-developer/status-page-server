package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.model.Site;
import com.pickax.status.page.server.dto.reseponse.site.SiteResponseDto;

import java.util.List;
import java.util.Optional;

public interface SiteRepositoryCustom {
	Optional<SiteResponseDto> getSiteResponse(long siteId, Long userId);

	List<Site> findByUserId(Long userId);

	Optional<Site> findBySiteIdAndUserId(Long siteId, Long userId);

	List<Site> findAllByUserId(Long userId);
}

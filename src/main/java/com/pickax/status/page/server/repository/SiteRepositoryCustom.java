package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.dto.reseponse.site.SiteResponseDto;

import java.util.Optional;

public interface SiteRepositoryCustom {
	Optional<SiteResponseDto> getSiteResponse(long siteId);
}

package com.pickax.status.page.server.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pickax.status.page.server.domain.model.MetaTag;
import com.pickax.status.page.server.domain.model.Site;
import com.pickax.status.page.server.repository.MetaTagRepository;
import com.pickax.status.page.server.repository.SiteRepository;
import com.pickax.status.page.server.dto.request.SiteCreateRequestDto;
import com.pickax.status.page.server.dto.reseponse.SiteResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SiteService {
	private static final int EXPIRATION_DAYS = 7;

	private final SiteRepository siteRepository;
	private final MetaTagRepository metaTagRepository;

	@Transactional
	public SiteResponseDto createSite(SiteCreateRequestDto siteCreateRequestDto) {
		Site site = Site.from(siteCreateRequestDto);

		MetaTag metaTag = createMetaTag();
		site.addMetaTag(metaTag);

		siteRepository.save(site);

		return SiteResponseDto.from(metaTag.getContent());
	}

	private MetaTag createMetaTag() {
		String content = UUID.randomUUID().toString();
		LocalDateTime expiredDate = LocalDateTime.now().plusDays(EXPIRATION_DAYS);

		MetaTag metaTag = MetaTag.of(content, expiredDate);
		metaTagRepository.save(metaTag);

		return metaTag;
	}
}

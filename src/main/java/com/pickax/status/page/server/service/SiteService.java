package com.pickax.status.page.server.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pickax.status.page.server.domain.model.MetaTag;
import com.pickax.status.page.server.domain.model.Site;
import com.pickax.status.page.server.repository.MetaTagRepository;
import com.pickax.status.page.server.repository.SiteRepository;
import com.pickax.status.page.server.dto.request.SiteRequestDto;
import com.pickax.status.page.server.dto.reseponse.SiteResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SiteService {

	private final SiteRepository siteRepository;
	private final MetaTagRepository metaTagRepository;

	@Transactional
	public SiteResponseDto createSite(SiteRequestDto siteRequestDto) {
		Site site = Site.from(siteRequestDto);

		MetaTag metaTag = createMetaTag();
		site.addMetaTag(metaTag);

		siteRepository.save(site);

		return SiteResponseDto.from(metaTag.getMetaTag());
	}

	private MetaTag createMetaTag() {
		String metaTagString = UUID.randomUUID().toString();

		MetaTag metaTag = MetaTag.from(metaTagString);
		metaTagRepository.save(metaTag);

		return metaTag;
	}
}

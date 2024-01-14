package com.pickax.status.page.server.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.dto.reseponse.DefaultSite;
import com.pickax.status.page.server.dto.reseponse.MetaTagValidation;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
	private static final String META_TAG_NAME = "quack-run-service-verification";
	private static final int EXPIRATION_DAYS = 7;

	private final SiteRepository siteRepository;
	private final MetaTagRepository metaTagRepository;

	@Transactional
	public SiteResponseDto createSite(SiteCreateRequestDto siteCreateRequestDto) {
		Site site = Site.from(siteCreateRequestDto);

		MetaTag metaTag = createMetaTag();
		site.addMetaTag(metaTag);

		siteRepository.save(site);

		return SiteResponseDto.from(metaTag.getId(), metaTag.getContent());
	}

	private MetaTag createMetaTag() {
		String content = UUID.randomUUID().toString();
		LocalDateTime expiredDate = LocalDateTime.now().plusDays(EXPIRATION_DAYS);

		MetaTag metaTag = MetaTag.of(content, expiredDate);
		metaTagRepository.save(metaTag);

		return metaTag;
	}

	@Transactional
	public void verifySite(long siteId) {
		Site site = siteRepository.findById(siteId)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SITE));

		if (!site.isUnverified()) {
			throw new CustomException(ErrorCode.INVALID_UNVERIFIED_SITE);
		}

		MetaTag metaTag = metaTagRepository.findFirstBySite_Id(siteId)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_META_TAG));

		try {
			checkMetaTag(site, metaTag);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	private void checkMetaTag(Site site, MetaTag metaTag) throws IOException {
		if (metaTag.isChecked()) {
			throw new CustomException(ErrorCode.INVALID_CHECKED_META_TAG);
		}

		if (metaTag.isExpired()) {
			throw new CustomException(ErrorCode.INVALID_EXPIRED_META_TAG);
		}

		Document doc = Jsoup.connect(site.getUrl()).get();
		Elements metaTags = doc.head().getElementsByTag("meta");

		for(Element meta: metaTags) {
			if(meta.attr("name").equals(META_TAG_NAME)){
				if (metaTag.isSameAs(meta.attr("content"))) {
					site.complete();
					metaTag.check();
					return;
				}
			}
		}

		throw new CustomException(ErrorCode.INVALID_META_TAG);
	}

	/**
	 * TODO. 로그인 API 붙일 때 유저아이디로 조회
	 * @param loggedInUserId
	 * @return
	 */
	public List<DefaultSite> findAllByUserId(Long loggedInUserId) {
//		List<Site> sitesFromRepository = this.siteRepository.findAllByUserId(loggedInUserId);
		List<Site> sitesFromRepository = this.siteRepository.findAll();
		List<DefaultSite> sites = new ArrayList<>();

		sitesFromRepository.forEach(
				site -> {
					sites.add(DefaultSite.builder()
							.id(site.getId())
							.name(site.getName())
							.url(site.getUrl())
							.ownerProofStatus(site.getSiteRegistrationStatus())
							.build());
				}
		);

		return sites;
	}

	public MetaTagValidation findValidMetaTag(Long siteId) {
		Site site = this.siteRepository.findById(siteId)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SITE));

		if (site.hasValidatedOwnerByMetaTag()) {
			throw new CustomException(ErrorCode.INVALID_COMPLETED_SITE);
		}

		MetaTag metaTagNotYetChecked = this.metaTagRepository.findMetaTagNotYetCheckedBySiteId(siteId, false)
				.stream()
				.findFirst()
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_META_TAG));

		return new MetaTagValidation(metaTagNotYetChecked.getId(), metaTagNotYetChecked.getContent());
	}
}

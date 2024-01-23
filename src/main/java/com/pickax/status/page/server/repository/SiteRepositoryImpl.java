package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.model.QSite;
import com.pickax.status.page.server.dto.reseponse.site.QSiteResponseDto;
import com.pickax.status.page.server.dto.reseponse.site.SiteResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.pickax.status.page.server.domain.model.QSite.site;

@Repository
@RequiredArgsConstructor
public class SiteRepositoryImpl implements SiteRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<SiteResponseDto> getSiteResponse(long siteId) {
        return Optional.ofNullable(queryFactory
                .select(new QSiteResponseDto(
                        site.id,
                        site.name,
                        site.description,
                        site.url,
                        site.siteRegistrationStatus
                ))
                .from(site)
                .where(site.id.eq(siteId))
                .fetchOne()
        );
    }
}

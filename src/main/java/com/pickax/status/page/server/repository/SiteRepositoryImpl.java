package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.model.Site;
import com.pickax.status.page.server.dto.reseponse.site.QSiteResponseDto;
import com.pickax.status.page.server.dto.reseponse.site.SiteResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.pickax.status.page.server.domain.model.QComponent.*;
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

    @Override
    public List<Site> findByUserId(Long userId) {
        return queryFactory
            .selectFrom(site)
            .leftJoin(site.components, component).fetchJoin()
            .where(site.user.id.eq(userId))
            .fetch();
    }

    @Override
    public Optional<Site> findBySiteIdAndUserId(Long siteId, Long userId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(site)
                .where(
                        site.id.eq(siteId),
                        site.user.id.eq(userId)
                )
                .fetchOne());
    }

    @Override
    public List<Site> findAllByUserId(Long userId) {
        return queryFactory
                .selectFrom(site)
                .where(site.user.id.eq(userId))
                .fetch();
    }
}

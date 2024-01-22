package com.pickax.status.page.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.pickax.status.page.server.domain.model.QComponentStatusLog.*;

@Repository
@RequiredArgsConstructor
public class StatusLogRepositoryImpl implements StatusLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findLatestComponentRiskLevel(Long componentId) {
        Long riskLevel = jpaQueryFactory
                .select(componentStatusLog.riskLevel)
                .from(componentStatusLog)
                .where(componentStatusLog.componentId.eq(componentId))
                .orderBy(componentStatusLog.schedulerRunDateTime.desc())
                .fetchFirst();

        if (riskLevel == null) {
            riskLevel = 0L;
        }

        return riskLevel;
    }
}

package com.pickax.status.page.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.pickax.status.page.server.domain.model.QStatusLog.*;

@Repository
@RequiredArgsConstructor
public class StatusLogRepositoryImpl implements StatusLogRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findLatestComponentRiskLevel(Long componentId) {
        Long riskLevel = jpaQueryFactory
                .select(statusLog.riskLevel)
                .from(statusLog)
                .where(statusLog.componentId.eq(componentId))
                .orderBy(statusLog.schedulerRunDateTime.desc())
                .fetchFirst();

        if (riskLevel == null) {
            riskLevel = 0L;
        }

        return riskLevel;
    }
}

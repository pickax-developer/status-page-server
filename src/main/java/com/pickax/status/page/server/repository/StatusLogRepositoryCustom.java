package com.pickax.status.page.server.repository;

public interface StatusLogRepositoryCustom {

    Long findLatestComponentRiskLevel(Long componentId);
}

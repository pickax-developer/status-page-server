package com.pickax.status.page.server.repository;

public interface ComponentStatusLogRepositoryCustom {

    Long findLatestComponentRiskLevel(Long componentId);
}

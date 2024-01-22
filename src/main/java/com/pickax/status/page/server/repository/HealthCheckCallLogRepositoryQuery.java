package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.dto.reseponse.LatestHealthCheckCallLogDto;

import java.util.List;

public interface HealthCheckCallLogRepositoryQuery {

    List<LatestHealthCheckCallLogDto> findLatestLogsByComponentId();
}
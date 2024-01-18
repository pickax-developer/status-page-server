package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.dto.reseponse.LatestHealthCheckRequestLogDto;

import java.util.List;

public interface HealthCheckRepositoryCustom {

    List<LatestHealthCheckRequestLogDto> findLatestLogsByComponentId();
}

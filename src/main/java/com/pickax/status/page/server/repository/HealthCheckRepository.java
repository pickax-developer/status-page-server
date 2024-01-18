package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.domain.model.HealthCheckRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthCheckRepository extends JpaRepository<HealthCheckRequestLog, Long>, HealthCheckRepositoryCustom {
}

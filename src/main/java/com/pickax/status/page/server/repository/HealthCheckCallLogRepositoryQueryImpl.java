package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.dto.LatestHealthCheckCallLogDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HealthCheckCallLogRepositoryQueryImpl implements HealthCheckCallLogRepositoryQuery {

    private final EntityManager entityManager;

    @Override
    public List<LatestHealthCheckCallLogDto> findLatestLogsByComponentId() {
        String sql = "" +
                "SELECT\n" +
                "       logs.id AS healthCheckCallLogId,\n" +
                "       logs.component_id AS componentId,\n" +
                "       logs.request_at AS latestRequestDateTime,\n" +
                "       c.frequency AS frequency,\n" +
                "       c.component_status AS componentStatus,\n" +
                "       c.name AS componentName,\n" +
                "       s.name AS siteName\n" +
                "FROM \n" +
                "     health_check_call_logs logs,\n" +
                "        (SELECT r.component_id, MAX(r.request_at) AS lastest_request_date\n" +
                "         FROM health_check_call_logs r\n" +
                "         GROUP BY r.component_id\n" +
                "         ) latest_logs\n" +
                "     INNER JOIN components c ON c.id = latest_logs.component_id AND c.is_active = true\n" +
                "     INNER JOIN sites s on c.site_id = s.id AND s.site_registration_status = 'COMPLETED'\n" +
                "WHERE\n" +
                "      logs.component_id = latest_logs.component_id\n" +
                "      AND logs.request_at = latest_logs.lastest_request_date";

        Query nativeQuery = entityManager.createNativeQuery(sql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();

        return jpaResultMapper.list(nativeQuery, LatestHealthCheckCallLogDto.class);
    }
}

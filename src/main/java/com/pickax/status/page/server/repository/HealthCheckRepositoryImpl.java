package com.pickax.status.page.server.repository;

import com.pickax.status.page.server.dto.reseponse.LatestHealthCheckRequestLogDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HealthCheckRepositoryImpl implements HealthCheckRepositoryCustom {

    private final EntityManager entityManager;


    @Override
    public List<LatestHealthCheckRequestLogDto> findLatestLogsByComponentId() {
        String sql = "" +
                "SELECT\n" +
                "       logs.id AS healthCheckRequestLogId,\n" +
                "       logs.component_id AS componentId,\n" +
                "       logs.request_at AS latestRequestDate,\n" +
                "       c.frequency AS frequency\n" +
                "FROM \n" +
                "     health_check_request_logs logs,\n" +
                "        (SELECT r.component_id, MAX(r.request_at) AS lastest_request_date\n" +
                "         FROM health_check_request_logs r\n" +
                "         GROUP BY r.component_id\n" +
                "         ) latest_logs\n" +
                "     LEFT JOIN components c ON c.id = latest_logs.component_id\n" +
                "WHERE\n" +
                "      logs.component_id = latest_logs.component_id\n" +
                "      AND logs.request_at = latest_logs.lastest_request_date";

        Query nativeQuery = entityManager.createNativeQuery(sql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();

        return jpaResultMapper.list(nativeQuery, LatestHealthCheckRequestLogDto.class);
    }
}

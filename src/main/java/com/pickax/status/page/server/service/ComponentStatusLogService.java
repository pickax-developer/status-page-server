package com.pickax.status.page.server.service;

import com.pickax.status.page.server.common.event.ComponentStatusInspectedEvent;
import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.common.exception.ErrorCode;
import com.pickax.status.page.server.domain.enumclass.ComponentStatus;
import com.pickax.status.page.server.domain.model.Component;
import com.pickax.status.page.server.domain.model.ComponentStatusLog;
import com.pickax.status.page.server.dto.LatestHealthCheckCallLogDto;
import com.pickax.status.page.server.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComponentStatusLogService {

    private final ComponentRepository componentRepository;
    private final ComponentStatusLogRepository componentStatusLogRepository;
    private final HealthCheckCallLogRepositoryQuery healthCheckCallLogRepositoryQuery;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void inspectHealthCheckCall() {
        List<LatestHealthCheckCallLogDto> latestHealthCheckCallLogs = healthCheckCallLogRepositoryQuery.findLatestLogsByComponentId();
        ComponentStatus currentComponentStatus;

        for (LatestHealthCheckCallLogDto latestHealthCheckLog : latestHealthCheckCallLogs) {
            LocalDateTime lastRequestDateTime = latestHealthCheckLog.getLatestRequestDateTime().toLocalDateTime();
            LocalDateTime timeLimit = lastRequestDateTime.plusSeconds(latestHealthCheckLog.getFrequency());
            LocalDateTime now = LocalDateTime.now();
            ComponentStatus previousComponentStatus = ComponentStatus.valueOf(latestHealthCheckLog.getComponentStatus());

            if (now.isBefore(timeLimit) || now.isEqual(timeLimit)) {
                currentComponentStatus = ComponentStatus.NO_ISSUES;
                saveStatusLog(latestHealthCheckLog, lastRequestDateTime, 0L, now, currentComponentStatus);
            } else {
                Long riskLevel = this.componentStatusLogRepository.findLatestComponentRiskLevel(latestHealthCheckLog.getComponentId());
                currentComponentStatus = makeComponentStatusByRiskLevel(riskLevel + 1);
                log.debug("risk level is {}, component status is {}", riskLevel + 1, currentComponentStatus);

                saveStatusLog(latestHealthCheckLog, lastRequestDateTime, riskLevel + 1, now, currentComponentStatus);
            }

            Component component = componentRepository.findById(latestHealthCheckLog.getComponentId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_COMPONENT));

            if (component.hasToBeChangedStatus(currentComponentStatus)) {
                component.changedStatus(currentComponentStatus);

                eventPublisher.publishEvent(ComponentStatusInspectedEvent.builder()
                        .previousComponentStatus(previousComponentStatus)
                        .currentComponentStatus(currentComponentStatus)
                        .componentName(latestHealthCheckLog.getComponentName())
                        .siteName(latestHealthCheckLog.getSiteName())
                        .username(null)
                        .build());
            }
        }

    }

    private ComponentStatus makeComponentStatusByRiskLevel(Long riskLevel) {
        if (0 <= riskLevel && riskLevel < 5) {
            return ComponentStatus.NO_ISSUES;
        }
        if (5 <= riskLevel && riskLevel < 10) {
            return ComponentStatus.WARN;
        }

        return ComponentStatus.OUTAGE;
    }

    private void saveStatusLog(LatestHealthCheckCallLogDto latestHealthCheckLog, LocalDateTime lastRequestDateTime, Long riskLevel, LocalDateTime now, ComponentStatus componentStatus) {
        this.componentStatusLogRepository.save(ComponentStatusLog.create(
                latestHealthCheckLog.getComponentId(),
                lastRequestDateTime,
                latestHealthCheckLog.getFrequency(),
                componentStatus,
                riskLevel,
                now
        ));
    }

}

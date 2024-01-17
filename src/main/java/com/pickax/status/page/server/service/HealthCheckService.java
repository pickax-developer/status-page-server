package com.pickax.status.page.server.service;

import com.pickax.status.page.server.common.exception.CustomException;
import com.pickax.status.page.server.domain.model.Component;
import com.pickax.status.page.server.domain.model.HealthCheckCallLog;
import com.pickax.status.page.server.dto.request.HealthCheckCallRequestDto;
import com.pickax.status.page.server.repository.ComponentRepository;
import com.pickax.status.page.server.repository.HealthCheckCallLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pickax.status.page.server.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class HealthCheckService {
    private static final int BEARER_PREFIX_LENGTH = 7;

    private final ComponentRepository componentRepository;
    private final HealthCheckCallLogRepository healthCheckCallLogRepository;

    @Transactional
    public void call(HealthCheckCallRequestDto healthCheckCallRequestDto, String authorization) {
        Component component = componentRepository.getComponent(healthCheckCallRequestDto.getComponentId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_COMPONENT));
        String secretKey = component.getSite().getSecretKey();

        if (authorization == null || !authorization.substring(BEARER_PREFIX_LENGTH).equals(secretKey)) {
            throw new CustomException(INVALID_SECRET_KEY);
        }

        healthCheckCallLogRepository.save(HealthCheckCallLog.create(component.getId()));
    }
}

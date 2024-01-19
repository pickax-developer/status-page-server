package com.pickax.status.page.server.controller;

import com.pickax.status.page.server.dto.request.HealthCheckCallRequestDto;
import com.pickax.status.page.server.service.HealthCheckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health-check")
public class HealthCheckController {

    private final HealthCheckService healthCheckService;

    @PostMapping
    public void requestForHealthCheck(
            @RequestBody @Valid HealthCheckCallRequestDto request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    ) {
        this.healthCheckService.call(request, authorization);
    }
}

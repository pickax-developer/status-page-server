package com.pickax.status.page.server.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthCheckCallRequestDto {
    @NotNull
    private Long componentId;

    public HealthCheckCallRequestDto(Long componentId) {
        this.componentId = componentId;
    }
}

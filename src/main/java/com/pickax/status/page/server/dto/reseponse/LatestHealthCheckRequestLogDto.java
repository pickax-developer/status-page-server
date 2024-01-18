package com.pickax.status.page.server.dto.reseponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
@NoArgsConstructor
public class LatestHealthCheckRequestLogDto {

    private Long healthCheckRequestLogId;

    private Long componentId;

    private Timestamp latestRequestDate;

    private Long frequency;


    public LatestHealthCheckRequestLogDto(Long healthCheckRequestLogId, Long componentId, Timestamp latestRequestDate, Long frequency) {
        this.healthCheckRequestLogId = healthCheckRequestLogId;
        this.componentId = componentId;
        this.latestRequestDate = latestRequestDate;
        this.frequency = frequency;
    }
}

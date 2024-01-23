package com.pickax.status.page.server.dto.reseponse.site;

import com.pickax.status.page.server.domain.enumclass.SiteRegistrationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SiteResponseDto {
    private Long id;
    private String name;
    private String description;
    private String url;
    private SiteRegistrationStatus status;

    @QueryProjection
    public SiteResponseDto(Long id, String name, String description, String url, SiteRegistrationStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.status = status;
    }
}

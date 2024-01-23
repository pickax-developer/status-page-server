package com.pickax.status.page.server.dto.reseponse.site;

import com.pickax.status.page.server.domain.enumclass.SiteRegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultSite {

    private Long id;

    private String name;

    private String url;

    private SiteRegistrationStatus ownerProofStatus;

}

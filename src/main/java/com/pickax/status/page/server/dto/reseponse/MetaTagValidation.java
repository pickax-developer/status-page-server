package com.pickax.status.page.server.dto.reseponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MetaTagValidation {

    private Long id;

    private String content;

    public MetaTagValidation(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}

package com.pickax.status.page.server.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class CreateComponentRequest {

    private String name;

    private String description;

    private Long frequency;

    private boolean isActive;

    @Setter
    @JsonIgnore
    private Long requesterId;

}

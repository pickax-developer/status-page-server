package com.pickax.status.page.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ComponentCreateRequestDto {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long frequency;

    @NotNull
    private boolean isActive;

}

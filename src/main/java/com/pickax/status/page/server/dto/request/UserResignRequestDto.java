package com.pickax.status.page.server.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserResignRequestDto(
	@NotNull
	String password
) {}

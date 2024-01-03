package com.pickax.status.page.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.pickax.status.page.server.dto.request.SiteCreateRequestDto;
import com.pickax.status.page.server.dto.reseponse.SiteResponseDto;
import com.pickax.status.page.server.service.SiteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/sites")
public class SiteController {

	private final SiteService siteService;

	@PostMapping
	public ResponseEntity<SiteResponseDto> createSite(
		@RequestBody @Valid SiteCreateRequestDto siteCreateRequestDto
	) {
		SiteResponseDto siteResponse = siteService.createSite(siteCreateRequestDto);
		return ResponseEntity.ok(siteResponse);
	}

	@GetMapping("{siteId}/verify")
	public void verifySite(@PathVariable long siteId) throws IOException {
		siteService.verifySite(siteId);
	}
}

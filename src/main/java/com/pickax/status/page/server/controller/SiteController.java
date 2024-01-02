package com.pickax.status.page.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pickax.status.page.server.dto.request.SiteRequestDto;
import com.pickax.status.page.server.dto.reseponse.SiteResponseDto;
import com.pickax.status.page.server.service.SiteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/sites")
public class SiteController {

	private final SiteService siteService;

	@PostMapping
	public ResponseEntity<SiteResponseDto> createSite(
		@RequestBody @Valid SiteRequestDto siteRequestDto
	) {
		SiteResponseDto serviceResponse = siteService.createSite(siteRequestDto);
		return ResponseEntity.ok(serviceResponse);
	}
}

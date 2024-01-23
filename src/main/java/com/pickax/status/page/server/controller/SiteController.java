package com.pickax.status.page.server.controller;

import com.pickax.status.page.server.dto.reseponse.site.SiteResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pickax.status.page.server.dto.reseponse.site.DefaultSite;
import com.pickax.status.page.server.dto.reseponse.MetaTagValidation;
import org.springframework.web.bind.annotation.*;

import com.pickax.status.page.server.dto.request.SiteCreateRequestDto;
import com.pickax.status.page.server.dto.reseponse.site.SiteCreateResponseDto;
import com.pickax.status.page.server.dto.reseponse.site.SiteSecretKeyResponseDto;
import com.pickax.status.page.server.service.SiteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/sites")
public class SiteController {

	private final SiteService siteService;

	@PostMapping
	public ResponseEntity<SiteCreateResponseDto> createSite(
		@RequestBody @Valid SiteCreateRequestDto siteCreateRequestDto
	) {
		SiteCreateResponseDto siteResponse = siteService.createSite(siteCreateRequestDto);
		return ResponseEntity.ok(siteResponse);
	}

	@PostMapping("{siteId}/verify")
	public void verifySite(@PathVariable long siteId) {
		siteService.verifySite(siteId);
	}

	@GetMapping
	public ResponseEntity<List<DefaultSite>> sitesByUserId() {
		// 로그인 기능이 없으므로 임시로 생성
		Long loggedInUserId = 1L;

		return ResponseEntity.ok(this.siteService.findAllByUserId(loggedInUserId));
	}

	@GetMapping("/{siteId}")
	public ResponseEntity<SiteResponseDto> getSite(@PathVariable long siteId) {
		return ResponseEntity.ok(this.siteService.getSite(siteId));
	}

	@GetMapping("/{siteId}/meta-tags")
	public ResponseEntity<MetaTagValidation> validateByMetaTag(@PathVariable Long siteId) {
		return ResponseEntity.ok(this.siteService.findValidMetaTag(siteId));
	}

	@GetMapping("/{siteId}/secret-key")
	public ResponseEntity<SiteSecretKeyResponseDto> getSecretKey(@PathVariable Long siteId) {
		return ResponseEntity.ok(this.siteService.getSecretKey(siteId));
	}
}

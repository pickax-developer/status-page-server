package com.pickax.status.page.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pickax.status.page.server.dto.reseponse.DefaultSite;
import com.pickax.status.page.server.dto.reseponse.MetaTagValidation;
import org.springframework.web.bind.annotation.*;

import com.pickax.status.page.server.dto.request.SiteCreateRequestDto;
import com.pickax.status.page.server.dto.reseponse.SiteResponseDto;
import com.pickax.status.page.server.service.SiteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import java.util.List;

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

	@GetMapping
	public ResponseEntity<List<DefaultSite>> sitesByUserId() {
		// 로그인 기능이 없으므로 임시로 생성
		Long loggedInUserId = 1L;

		return ResponseEntity.ok(this.siteService.findAllByUserId(loggedInUserId));
	}

	@GetMapping("/{siteId}/meta-tags")
	public ResponseEntity<MetaTagValidation> validateByMetaTag(@PathVariable Long siteId) {
		return ResponseEntity.ok(this.siteService.findValidMetaTag(siteId));
	}

}

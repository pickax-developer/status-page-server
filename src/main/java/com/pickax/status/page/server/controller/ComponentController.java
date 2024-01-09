package com.pickax.status.page.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pickax.status.page.server.dto.reseponse.ComponentResponseDto;
import com.pickax.status.page.server.dto.reseponse.DefaultSite;
import com.pickax.status.page.server.service.ComponentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/sites")
public class ComponentController {
	private final ComponentService componentService;

	@GetMapping("/{siteId}/components")
	public ResponseEntity<List<ComponentResponseDto>> getComponents(@PathVariable Long siteId) {
		return ResponseEntity.ok(componentService.getComponents(siteId));
	}
}

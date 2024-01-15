package com.pickax.status.page.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.pickax.status.page.server.dto.request.ComponentCreateRequestDto;
import com.pickax.status.page.server.dto.reseponse.component.ComponentListResponseDto;
import com.pickax.status.page.server.dto.reseponse.component.ComponentResponseDto;
import com.pickax.status.page.server.service.ComponentService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.pickax.status.page.server.dto.reseponse.component.ComponentActiveListResponseDto;
import com.pickax.status.page.server.dto.reseponse.component.ComponentActiveResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/sites")
public class ComponentController {

    private final ComponentService componentService;

    @PostMapping("/{siteId}/components")
    public void createComponent(@PathVariable Long siteId, @RequestBody @Valid ComponentCreateRequestDto request) {
        Long loggedInUserId = 1L;
        this.componentService.createComponent(siteId, request, loggedInUserId);
    }

	@GetMapping("/{siteId}/components/active")
	public ResponseEntity<ComponentActiveListResponseDto> getActiveComponents(@PathVariable Long siteId) {
		List<ComponentActiveResponseDto> components = componentService.getActiveComponents(siteId);
		ComponentActiveListResponseDto componentActiveListResponseDto = new ComponentActiveListResponseDto(components);
		return ResponseEntity.ok(componentActiveListResponseDto);
	}

	@GetMapping("/{siteId}/components")
	public ResponseEntity<ComponentListResponseDto> getComponents(@PathVariable Long siteId) {
		List<ComponentResponseDto> components = componentService.getComponents(siteId);
		ComponentListResponseDto componentListResponseDto = new ComponentListResponseDto(components);
		return ResponseEntity.ok(componentListResponseDto);
	}
}

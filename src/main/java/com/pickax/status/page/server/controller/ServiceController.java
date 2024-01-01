package com.pickax.status.page.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pickax.status.page.server.dto.request.ServiceRequestDto;
import com.pickax.status.page.server.dto.reseponse.ServiceResponseDto;
import com.pickax.status.page.server.service.ServiceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/services")
public class ServiceController {

	private final ServiceService serviceService;

	@PostMapping
	public ResponseEntity<ServiceResponseDto> createService(
		@RequestBody @Valid ServiceRequestDto serviceRequestDto
	) {
		ServiceResponseDto serviceResponse = serviceService.createService(serviceRequestDto);
		return ResponseEntity.ok(serviceResponse);
	}
}

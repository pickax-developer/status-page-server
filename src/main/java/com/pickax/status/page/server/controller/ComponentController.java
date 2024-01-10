package com.pickax.status.page.server.controller;

import com.pickax.status.page.server.dto.request.ComponentCreateRequestDto;
import com.pickax.status.page.server.service.ComponentService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}

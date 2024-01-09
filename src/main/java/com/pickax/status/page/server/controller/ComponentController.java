package com.pickax.status.page.server.controller;

import com.pickax.status.page.server.dto.request.CreateComponentRequest;
import com.pickax.status.page.server.service.ComponentService;
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
    public void createComponent(@PathVariable Long siteId, @RequestBody CreateComponentRequest request) {
        request.setRequesterId(1L);
        this.componentService.createComponent(siteId, request);
    }
}

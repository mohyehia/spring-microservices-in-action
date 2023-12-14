package com.moh.yehia.organization.service.controller;

import com.moh.yehia.organization.service.exception.NotFoundException;
import com.moh.yehia.organization.service.mapper.OrganizationMapper;
import com.moh.yehia.organization.service.model.OrganizationModel;
import com.moh.yehia.organization.service.model.OrganizationRequest;
import com.moh.yehia.organization.service.service.design.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    private final OrganizationService organizationService;
    private final OrganizationMapper organizationMapper;


    @GetMapping("/{slug}")
    public Mono<OrganizationModel> findBySlug(@PathVariable("slug") String slug) {
        return organizationService.findBySlug(slug)
                .map(organizationMapper::mapToOrganizationModel)
                .switchIfEmpty(Mono.error(new NotFoundException("Organization not found with slug " + slug)));
    }

    @PostMapping
    public Mono<OrganizationModel> save(@RequestBody OrganizationRequest organizationRequest) {
        return organizationService.save(organizationRequest)
                .map(organizationMapper::mapToOrganizationModel)
                .switchIfEmpty(Mono.empty());
    }
}

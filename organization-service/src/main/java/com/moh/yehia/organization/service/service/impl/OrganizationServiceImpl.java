package com.moh.yehia.organization.service.service.impl;

import com.moh.yehia.organization.service.model.Organization;
import com.moh.yehia.organization.service.model.OrganizationRequest;
import com.moh.yehia.organization.service.repository.OrganizationRepository;
import com.moh.yehia.organization.service.service.design.OrganizationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Override
    @CircuitBreaker(name = "organization-service")
    public Mono<Organization> findBySlug(String slug) {
        return organizationRepository.findBySlug(slug)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    @CircuitBreaker(name = "organization-service")
    public Mono<Organization> save(OrganizationRequest organizationRequest) {
        return organizationRepository.save(Organization.builder()
                .name(organizationRequest.name())
                .slug(organizationRequest.slug())
                .contactEmail(organizationRequest.email())
                .contactPhone(organizationRequest.phone())
                .createdAt(LocalDateTime.now())
                .build());
    }
}

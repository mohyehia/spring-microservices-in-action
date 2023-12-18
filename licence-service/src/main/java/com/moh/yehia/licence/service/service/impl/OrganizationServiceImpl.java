package com.moh.yehia.licence.service.service.impl;

import com.moh.yehia.licence.service.exception.NotFoundException;
import com.moh.yehia.licence.service.model.OrganizationDTO;
import com.moh.yehia.licence.service.service.design.OrganizationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrganizationServiceImpl implements OrganizationService {
    private final WebClient webClient;

    @Override
    @CircuitBreaker(name = "organization-service")
    public Mono<OrganizationDTO> findOne(String organizationId) {
        return webClient.get()
                .uri("http://organization-service/api/v1/organizations/{organizationSlug}", organizationId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new NotFoundException("Organization not found")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Server error")))
                .bodyToMono(OrganizationDTO.class);
    }
}

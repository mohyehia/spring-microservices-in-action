package com.moh.yehia.licence.service.service.impl;

import com.moh.yehia.licence.service.constant.AppConstants;
import com.moh.yehia.licence.service.exception.NotFoundException;
import com.moh.yehia.licence.service.model.OrganizationDTO;
import com.moh.yehia.licence.service.service.design.OrganizationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrganizationServiceImpl implements OrganizationService {
    private final WebClient webClient;
    private final MessageSource messageSource;
    private final ObservationRegistry observationRegistry;
    private final Tracer tracer;

    @Value("${organization.base.api}")
    private String organizationAPI;

    @Override
    @CircuitBreaker(name = "organization-service", fallbackMethod = "fallbackFindOne")
    @Retry(name = "organization-service", fallbackMethod = "fallbackFindOne")
    public Mono<OrganizationDTO> findOne(String organizationId) {
        var observation = Observation.createNotStarted("organization-lookup", observationRegistry);
        return Objects.requireNonNull(observation.observe(() ->
                        Mono.defer(() -> webClient.get()
                                .uri(organizationAPI, organizationId)
                                .retrieve()
                                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new NotFoundException("Organization not found")))
                                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Server error")))
                                .bodyToMono(OrganizationDTO.class))
                ))
                .doFinally(signalType -> observation.stop())
                .contextWrite(context -> context.put(ObservationThreadLocalAccessor.KEY, observation));
    }

    private Mono<OrganizationDTO> fallbackFindOne(String organizationId, Throwable throwable) {
        log.error("Fallback method called for findOne with organizationId: {}", organizationId);
        log.error(throwable.getMessage(), throwable);
        return Mono.just(new OrganizationDTO(123L,
                AppConstants.ORGANIZATION_NOT_AVAILABLE,
                AppConstants.ORGANIZATION_NOT_AVAILABLE,
                AppConstants.ORGANIZATION_NOT_AVAILABLE,
                AppConstants.ORGANIZATION_NOT_AVAILABLE,
                Timestamp.valueOf(LocalDateTime.now())));
    }
}

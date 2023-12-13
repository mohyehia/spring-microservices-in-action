package com.moh.yehia.organization.service.service.design;

import com.moh.yehia.organization.service.model.Organization;
import com.moh.yehia.organization.service.model.OrganizationRequest;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;
import reactor.core.publisher.Mono;

public interface OrganizationService {
    Mono<Organization> findBySlug(String slug);

    Mono<Organization> save(OrganizationRequest organizationRequest);
}

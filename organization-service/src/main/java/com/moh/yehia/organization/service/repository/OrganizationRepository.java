package com.moh.yehia.organization.service.repository;

import com.moh.yehia.organization.service.model.Organization;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface OrganizationRepository extends R2dbcRepository<Organization, Long> {
    Mono<Organization> findBySlug(String slug);
}

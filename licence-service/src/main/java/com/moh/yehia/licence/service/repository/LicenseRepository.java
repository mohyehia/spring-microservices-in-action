package com.moh.yehia.licence.service.repository;

import com.moh.yehia.licence.service.model.License;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LicenseRepository extends ReactiveCrudRepository<License, String> {
    Mono<License> findByIdAndOrganizationId(String licenceId, String organizationId);

    Mono<Void> deleteByIdAndOrganizationId(String licenseId, String organizationId);

    Flux<License> findAllByOrganizationId(String organizationId);
}

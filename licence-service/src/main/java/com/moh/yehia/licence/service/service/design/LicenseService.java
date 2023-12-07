package com.moh.yehia.licence.service.service.design;

import com.moh.yehia.licence.service.model.License;
import com.moh.yehia.licence.service.model.LicenseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LicenseService {
    Mono<License> findOne(String organizationId, String licenceId);

    Mono<License> save(String organizationId, LicenseDTO licenseDto);

    Mono<License> update(String organizationId, String licenseId, LicenseDTO licenseDTO);

    Mono<Void> delete(String organizationId, String licenceId);

    Flux<License> findByOrganizationId(String organizationId);
}

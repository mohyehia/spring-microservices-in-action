package com.moh.yehia.licence.service.service.design;

import com.moh.yehia.licence.service.model.OrganizationDTO;
import reactor.core.publisher.Mono;

public interface OrganizationService {
    Mono<OrganizationDTO> findOne(String organizationId);
}

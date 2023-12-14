package com.moh.yehia.licence.service.service.impl;

import com.moh.yehia.licence.service.model.License;
import com.moh.yehia.licence.service.model.LicenseDTO;
import com.moh.yehia.licence.service.repository.LicenseRepository;
import com.moh.yehia.licence.service.service.design.LicenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Log4j2
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {
    private final LicenseRepository licenseRepository;

    @Override
    public Mono<License> findOne(String organizationId, String licenceId) {
        return licenseRepository.findByIdAndOrganizationId(licenceId, organizationId)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<License> save(String organizationId, LicenseDTO licenseDto) {
        License license = License.builder()
                .organizationId(organizationId)
                .productName(licenseDto.getProductName())
                .licenceType(licenseDto.getLicenceType())
                .description(licenseDto.getDescription())
                .build();
        return licenseRepository.save(license);
    }

    @Override
    public Mono<License> update(String organizationId, String licenseId, LicenseDTO licenseDTO) {
        return licenseRepository.findByIdAndOrganizationId(licenseId, organizationId)
                .flatMap(license -> {
                    if (licenseDTO.getDescription() != null) license.setDescription(licenseDTO.getDescription());
                    if (licenseDTO.getLicenceType() != null) license.setLicenceType(licenseDTO.getLicenceType());
                    if (licenseDTO.getProductName() != null) license.setProductName(licenseDTO.getProductName());
                    return licenseRepository.save(license);
                }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> delete(String organizationId, String licenceId) {
        return licenseRepository.deleteByIdAndOrganizationId(licenceId, organizationId);
    }

    @Override
    public Flux<License> findByOrganizationId(String organizationId) {
        return licenseRepository.findAllByOrganizationId(organizationId);
    }
}

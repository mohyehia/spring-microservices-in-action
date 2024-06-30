package com.moh.yehia.licence.service.repository;

import com.moh.yehia.licence.service.model.License;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@DataMongoTest
class LicenseRepositoryTest extends BaseMongoContainer {

    @Autowired
    private LicenseRepository licenseRepository;

    @AfterEach
    void cleanUp() {
        licenseRepository.deleteAll().block();
    }

    @Test
    void shouldReturnLicenses() {
        Flux<License> licenseFlux = licenseRepository.saveAll(List.of(License.builder()
                        .organizationId("dummy-organization-id")
                        .description("some description")
                        .productName("some product")
                        .licenceType("full").build()))
                .flatMap(res -> licenseRepository.findAll());
        StepVerifier
                .create(licenseFlux)
                .assertNext(license -> {
                    Assertions.assertThat(license).isNotNull();
                    Assertions.assertThat(license.getId())
                            .isNotNull()
                            .isNotEmpty();
                    Assertions.assertThat(license.getOrganizationId())
                            .isNotNull()
                            .isNotEmpty()
                            .isEqualTo("dummy-organization-id");
                    Assertions.assertThat(license.getDescription())
                            .isNotNull()
                            .isNotEmpty()
                            .isEqualTo("some description");
                    Assertions.assertThat(license.getProductName())
                            .isNotNull()
                            .isNotEmpty()
                            .isEqualTo("some product");
                    Assertions.assertThat(license.getLicenceType())
                            .isNotNull()
                            .isNotEmpty()
                            .isEqualTo("full");
                })
                .verifyComplete();
    }

    @Test
    void givenLicense_whenSaveLicense_thenLicenseSaved() {
        Mono<License> licenseMono = licenseRepository.save(License.builder()
                        .organizationId("dummy-organization-id")
                        .description("some description")
                        .productName("some product")
                        .licenceType("part").build())
                .flatMap(res -> licenseRepository.findByIdAndOrganizationId(res.getId(), res.getOrganizationId()));

        StepVerifier
                .create(licenseMono)
                .assertNext(license -> {
                    Assertions.assertThat(license).isNotNull();
                    Assertions.assertThat(license.getId())
                            .isNotNull()
                            .isNotEmpty();
                    Assertions.assertThat(license.getOrganizationId())
                            .isNotNull()
                            .isNotEmpty()
                            .isEqualTo("dummy-organization-id");
                    Assertions.assertThat(license.getDescription())
                            .isNotNull()
                            .isNotEmpty()
                            .isEqualTo("some description");
                    Assertions.assertThat(license.getProductName())
                            .isNotNull()
                            .isNotEmpty()
                            .isEqualTo("some product");
                    Assertions.assertThat(license.getLicenceType())
                            .isNotNull()
                            .isNotEmpty()
                            .isEqualTo("part");
                })
                .verifyComplete();
    }

    @Test
    void givenLicenseList_whenFindAllByOrganizationId_thenReturnListOfLicenses() {
        Flux<License> licenseFlux = licenseRepository.saveAll(List.of(License.builder()
                                .organizationId("dummy-organization-id")
                                .description("some description")
                                .productName("some product")
                                .licenceType("full").build(),
                        License.builder()
                                .organizationId("another-organization-id")
                                .description("some description")
                                .productName("some product")
                                .licenceType("part").build()))
                .flatMap(res -> licenseRepository.findByIdAndOrganizationId(res.getId(), "dummy-organization-id"));
        StepVerifier
                .create(licenseFlux)
                .assertNext(license -> {
                    Assertions.assertThat(license).isNotNull();
                    Assertions.assertThat(license.getId())
                            .isNotNull()
                            .isNotEmpty();
                    Assertions.assertThat(license.getOrganizationId())
                            .isNotNull()
                            .isNotEmpty()
                            .isEqualTo("dummy-organization-id");
                    Assertions.assertThat(license.getLicenceType())
                            .isNotNull()
                            .isNotEmpty()
                            .isEqualTo("full");
                })
                .verifyComplete();
    }
}
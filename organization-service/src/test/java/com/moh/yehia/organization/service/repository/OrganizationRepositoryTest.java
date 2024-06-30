package com.moh.yehia.organization.service.repository;

import com.moh.yehia.organization.service.model.Organization;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@DataR2dbcTest
class OrganizationRepositoryTest extends BasePostgresqlContainer {
    @Autowired
    private OrganizationRepository organizationRepository;

    @AfterEach
    void cleanUp() {
        organizationRepository.deleteAll().block();
    }

    @Test
    void givenOrganization_whenSave_thenReturnSavedOrganization() {
        Flux<Organization> organizationFlux = organizationRepository.save(Organization.builder()
                        .name("some organization")
                        .slug("some-slug")
                        .createdAt(LocalDateTime.now())
                        .contactEmail("some contact email")
                        .contactPhone("some contact phone")
                        .build())
                .flatMapMany(res -> organizationRepository.findAll());

        StepVerifier
                .create(organizationFlux)
                .consumeNextWith(organization -> {
                    Assertions.assertThat(organization).isNotNull();
                    Assertions.assertThat(organization.getId()).isPositive();
                    Assertions.assertThat(organization.getName()).isNotNull()
                            .isNotEmpty().isEqualTo("some organization");
                    Assertions.assertThat(organization.getSlug()).isNotNull()
                            .isNotEmpty().isEqualTo("some-slug");
                    Assertions.assertThat(organization.getContactEmail()).isNotNull()
                            .isNotEmpty().isEqualTo("some contact email");
                    Assertions.assertThat(organization.getContactPhone()).isNotNull()
                            .isNotEmpty().isEqualTo("some contact phone");
                })
                .verifyComplete();
    }

    @Test
    void givenSavedOrganization_whenFindBySlug_thenReturnSavedOrganization() {
        Mono<Organization> organizationMono = organizationRepository.save(Organization.builder()
                        .name("some organization")
                        .slug("some-slug")
                        .createdAt(LocalDateTime.now())
                        .contactEmail("some contact email")
                        .contactPhone("some contact phone")
                        .build())
                .flatMap(res -> organizationRepository.findBySlug(res.getSlug()).log());
        StepVerifier
                .create(organizationMono)
                .consumeNextWith(organization -> {
                    Assertions.assertThat(organization).isNotNull();
                    Assertions.assertThat(organization.getId()).isPositive();
                    Assertions.assertThat(organization.getName()).isNotNull()
                            .isNotEmpty().isEqualTo("some organization");
                    Assertions.assertThat(organization.getSlug()).isNotNull()
                            .isNotEmpty().isEqualTo("some-slug");
                    Assertions.assertThat(organization.getContactEmail()).isNotNull()
                            .isNotEmpty().isEqualTo("some contact email");
                    Assertions.assertThat(organization.getContactPhone()).isNotNull()
                            .isNotEmpty().isEqualTo("some contact phone");
                })
                .verifyComplete();
    }
}

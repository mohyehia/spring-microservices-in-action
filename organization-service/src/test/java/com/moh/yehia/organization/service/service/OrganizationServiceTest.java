package com.moh.yehia.organization.service.service;

import com.moh.yehia.organization.service.model.Organization;
import com.moh.yehia.organization.service.model.OrganizationRequest;
import com.moh.yehia.organization.service.repository.OrganizationRepository;
import com.moh.yehia.organization.service.service.impl.OrganizationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;
    @InjectMocks
    private OrganizationServiceImpl organizationService;

    @Test
    void givenSlug_whenFindBySlug_thenReturnOrganization() {
        var organization = Organization.builder()
                .name("some name")
                .slug("some-slug")
                .contactEmail("some email")
                .contactPhone("some phone")
                .build();
        BDDMockito.given(organizationRepository.findBySlug(ArgumentMatchers.anyString())).willReturn(Mono.just(organization));

        StepVerifier
                .create(organizationService.findBySlug("some-slug"))
                .assertNext(org -> {
                    Assertions.assertThat(org).isNotNull();
                    Assertions.assertThat(org.getName()).isEqualTo(organization.getName());
                    Assertions.assertThat(org.getContactEmail()).isEqualTo(organization.getContactEmail());
                    Assertions.assertThat(org.getContactPhone()).isEqualTo(organization.getContactPhone());
                })
                .verifyComplete();
    }

    @Test
    void givenValidOrganizationRequest_whenSaveOrganization_thenReturnSavedOrganization() {
        var organizationRequest = new OrganizationRequest("some name", "some-slug", "some email", "some phone");
        var organization = Organization.builder()
                .name(organizationRequest.name())
                .slug(organizationRequest.slug())
                .contactEmail(organizationRequest.email())
                .contactPhone(organizationRequest.phone())
                .build();
        BDDMockito.given(organizationRepository.save(ArgumentMatchers.any(Organization.class))).willReturn(Mono.just(organization));

        StepVerifier
                .create(organizationService.save(organizationRequest))
                .assertNext(org -> {
                    Assertions.assertThat(org).isNotNull();
                    Assertions.assertThat(org.getName()).isEqualTo(organizationRequest.name());
                    Assertions.assertThat(org.getContactEmail()).isEqualTo(organizationRequest.email());
                    Assertions.assertThat(org.getContactPhone()).isEqualTo(organizationRequest.phone());
                })
                .verifyComplete();
    }
}
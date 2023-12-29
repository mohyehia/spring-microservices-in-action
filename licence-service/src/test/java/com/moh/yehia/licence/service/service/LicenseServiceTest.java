package com.moh.yehia.licence.service.service;

import com.moh.yehia.licence.service.model.License;
import com.moh.yehia.licence.service.model.LicenseDTO;
import com.moh.yehia.licence.service.repository.LicenseRepository;
import com.moh.yehia.licence.service.service.impl.LicenseServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class LicenseServiceTest {

    @Mock
    private LicenseRepository licenseRepository;

    @InjectMocks
    private LicenseServiceImpl licenseService;

    @Test
    void shouldReturnLicenseByOrganizationIdAndLicenseId() {
        // mock
        License license = License.builder()
                .id(UUID.randomUUID().toString())
                .organizationId("primary")
                .productName("some random product")
                .description("some random description")
                .licenceType("full")
                .build();
        // given
        BDDMockito.given(licenseRepository.findByIdAndOrganizationId(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(Mono.just(license));
        // when & then or assertions
        StepVerifier
                .create(licenseService.findOne("primary", "123456789"))
                .assertNext(ls -> {
                    Assertions.assertThat(ls).isNotNull();
                    Assertions.assertThat(ls.getOrganizationId()).isEqualTo(license.getOrganizationId());
                    Assertions.assertThat(ls.getProductName()).isEqualTo(license.getProductName());
                })
                .verifyComplete();
    }

    @Test
    void shouldSaveLicenseWithValidData() {
        // mock
        var license = License.builder()
                .id(UUID.randomUUID().toString())
                .organizationId("primary")
                .productName("some random product")
                .description("some random description")
                .licenceType("full")
                .build();
        var licenseDTO = new LicenseDTO("primary", "some random description", "some random product", "full");
        // given
        BDDMockito.given(licenseRepository.save(ArgumentMatchers.any(License.class))).willReturn(Mono.just(license));
        // when & then or assertions
        StepVerifier
                .create(licenseService.save("primary", licenseDTO))
                .assertNext(ls -> {
                    Assertions.assertThat(ls).isNotNull();
                    Assertions.assertThat(ls.getOrganizationId()).isEqualTo(license.getOrganizationId());
                    Assertions.assertThat(ls.getProductName()).isEqualTo(license.getProductName());
                    Assertions.assertThat(ls.getDescription()).isEqualTo(license.getDescription());
                    Assertions.assertThat(ls.getLicenceType()).isEqualTo(license.getLicenceType());
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateLicense() {
        // mock
        var license = License.builder()
                .id(UUID.randomUUID().toString())
                .organizationId("primary")
                .productName("some random product")
                .description("some random description")
                .licenceType("full")
                .build();
        // given
        BDDMockito.given(licenseRepository.findByIdAndOrganizationId(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(Mono.just(license));
        BDDMockito.given(licenseRepository.save(ArgumentMatchers.any(License.class))).willReturn(Mono.just(license));
        // when & then or assertions
        StepVerifier
                .create(licenseService.update("primary", "123456789", new LicenseDTO("primary", "some random description", "some random product", "full")))
                .assertNext(ls -> {
                    Assertions.assertThat(ls).isNotNull();
                    Assertions.assertThat(ls.getOrganizationId()).isEqualTo(license.getOrganizationId());
                    Assertions.assertThat(ls.getProductName()).isEqualTo(license.getProductName());
                    Assertions.assertThat(ls.getDescription()).isEqualTo(license.getDescription());
                    Assertions.assertThat(ls.getLicenceType()).isEqualTo(license.getLicenceType());
                })
                .verifyComplete();
    }

    @Test
    void shouldDeleteLicense() {
        // given
        BDDMockito.given(licenseRepository.deleteByIdAndOrganizationId(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(Mono.empty());
        // when & then or assertions
        StepVerifier
                .create(licenseRepository.deleteByIdAndOrganizationId("id", "primary"))
                .verifyComplete();
        Mockito.verify(licenseRepository).deleteByIdAndOrganizationId("id", "primary");
    }

    @Test
    void shouldReturnLicensesByOrganizationId() {
        // mock
        var licenses = List.of(License.builder()
                .id(UUID.randomUUID().toString())
                .organizationId("primary")
                .productName("some random product")
                .description("some random description")
                .licenceType("full")
                .build(), License.builder()
                .id(UUID.randomUUID().toString())
                .organizationId("primary")
                .productName("some random product")
                .description("some random description")
                .licenceType("full")
                .build());
        // given
        BDDMockito.given(licenseRepository.findAllByOrganizationId(ArgumentMatchers.anyString())).willReturn(Flux.fromIterable(licenses));

        // when & then or assertions
        StepVerifier
                .create(licenseService.findByOrganizationId("primary"))
                .expectNextMatches(license -> license.getOrganizationId().equals("primary"))
                .expectNextMatches(license -> license.getProductName().equals("some random product"))
                .expectComplete()
                .verify();
    }
}
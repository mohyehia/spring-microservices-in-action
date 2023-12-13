package com.moh.yehia.licence.service.service;

import com.moh.yehia.licence.service.model.License;
import com.moh.yehia.licence.service.repository.LicenseRepository;
import com.moh.yehia.licence.service.service.impl.LicenseServiceImpl;
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
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByOrganizationId() {
    }
}
package com.moh.yehia.licence.service.mapper;

import com.moh.yehia.licence.service.model.License;
import com.moh.yehia.licence.service.model.LicenseModel;
import com.moh.yehia.licence.service.model.OrganizationDTO;
import org.springframework.stereotype.Component;

@Component
public class LicenseMapper {

    public LicenseModel mapToLicenceModel(License license) {
        return LicenseModel.builder()
                .id(license.getId())
                .organizationId(license.getOrganizationId())
                .description(license.getDescription())
                .productName(license.getProductName())
                .licenceType(license.getLicenceType())
                .build();
    }

    public LicenseModel mapToLicenceModel(License license, OrganizationDTO organizationDTO) {
        return new LicenseModel(license.getId(),
                license.getOrganizationId(),
                license.getDescription(),
                license.getProductName(),
                license.getLicenceType(),
                organizationDTO.contactEmail(),
                organizationDTO.contactPhone());
    }
}

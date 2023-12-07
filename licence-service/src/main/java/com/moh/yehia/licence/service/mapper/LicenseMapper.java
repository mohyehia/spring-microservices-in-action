package com.moh.yehia.licence.service.mapper;

import com.moh.yehia.licence.service.model.License;
import com.moh.yehia.licence.service.model.LicenseModel;
import org.springframework.stereotype.Component;

@Component
public class LicenseMapper {
    public LicenseModel mapToLicenceModel(License license) {
        return new LicenseModel(license.getId(), license.getOrganizationId(), license.getDescription(), license.getProductName(), license.getLicenceType());
    }
}

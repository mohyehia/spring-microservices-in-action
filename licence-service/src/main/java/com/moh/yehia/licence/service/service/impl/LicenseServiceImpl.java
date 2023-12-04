package com.moh.yehia.licence.service.service.impl;

import com.moh.yehia.licence.service.model.License;
import com.moh.yehia.licence.service.service.design.LicenseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Log4j2
public class LicenseServiceImpl implements LicenseService {
    private final Random random = new Random();

    @Override
    public License findOne(String organizationId, long licenceId) {
        return new License(licenceId, organizationId, "Software product", "Ostock", "full");
    }

    @Override
    public License save(String organizationId, License license) {
        return new License(this.random.nextLong(1, 1000000), organizationId, license.getDescription(), license.getProductName(), license.getLicenceType());
    }

    @Override
    public License update(String organizationId, License license) {
        return new License(this.random.nextLong(1, 1000000), organizationId, license.getDescription(), license.getProductName(), license.getLicenceType());
    }

    @Override
    public void delete(String organizationId, long licenceId) {
        log.info("license deleted!");
    }
}

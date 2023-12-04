package com.moh.yehia.licence.service.service.design;

import com.moh.yehia.licence.service.model.License;

public interface LicenseService {
    License findOne(String organizationId, long licenceId);

    License save(String organizationId, License license);

    License update(String organizationId, License license);

    void delete(String organizationId, long licenceId);
}

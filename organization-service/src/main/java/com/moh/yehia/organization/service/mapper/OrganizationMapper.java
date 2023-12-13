package com.moh.yehia.organization.service.mapper;

import com.moh.yehia.organization.service.model.Organization;
import com.moh.yehia.organization.service.model.OrganizationModel;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {
    public OrganizationModel mapToOrganizationModel(Organization organization) {
        return new OrganizationModel(organization.getId(), organization.getName(), organization.getSlug(), organization.getCreatedAt());
    }
}

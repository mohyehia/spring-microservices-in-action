package com.moh.yehia.licence.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseModel extends RepresentationModel<LicenseModel> {
    private String id;
    private String organizationId;
    private String description;
    private String productName;
    private String licenceType;
}

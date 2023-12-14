package com.moh.yehia.licence.service.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenseModel extends RepresentationModel<LicenseModel> {
    private String id;
    private String organizationId;
    private String description;
    private String productName;
    private String licenceType;
    private String contactEmail;
    private String contactPhone;
}

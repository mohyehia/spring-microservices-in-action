package com.moh.yehia.licence.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseDTO {
    private String organizationId;
    private String description;
    private String productName;
    private String licenceType;
}

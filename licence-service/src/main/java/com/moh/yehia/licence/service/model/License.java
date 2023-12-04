package com.moh.yehia.licence.service.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class License extends RepresentationModel<License> {
    private long id;
    private String organizationId;
    private String description;
    private String productName;
    private String licenceType;
}

package com.moh.yehia.licence.service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public final class License {
    @Id
    private String id;
    private String organizationId;
    private String description;
    private String productName;
    private String licenceType;
}

package com.moh.yehia.organization.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationModel {
    private long id;
    private String name;
    private String slug;
    private LocalDateTime createdAt;
}

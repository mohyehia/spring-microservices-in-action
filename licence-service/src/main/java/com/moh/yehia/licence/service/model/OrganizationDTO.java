package com.moh.yehia.licence.service.model;

import java.sql.Timestamp;

public record OrganizationDTO(long id, String name, String slug, String contactEmail, String contactPhone, Timestamp createdAt) {
}

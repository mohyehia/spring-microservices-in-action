package com.moh.yehia.organization.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("organizations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization {
    @Id
    @Column("id")
    private long id;

    @Column("name")
    private String name;

    @Column("slug")
    private String slug;

    @Column("created_at")
    private LocalDateTime createdAt;
}

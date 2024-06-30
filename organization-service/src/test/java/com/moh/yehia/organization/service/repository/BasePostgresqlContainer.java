package com.moh.yehia.organization.service.repository;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

public class BasePostgresqlContainer {
    static final PostgreSQLContainer<?> POSTGRES_QL_CONTAINER;

    static {
        POSTGRES_QL_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.2"))
                .withUsername("organization-user")
                .withPassword("P@ssw0rd")
                .withDatabaseName("organization-db");
        POSTGRES_QL_CONTAINER.setPortBindings(List.of("5432:5432"));
        POSTGRES_QL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
                + POSTGRES_QL_CONTAINER.getHost() + ":" + POSTGRES_QL_CONTAINER.getFirstMappedPort()
                + "/" + POSTGRES_QL_CONTAINER.getDatabaseName());
        dynamicPropertyRegistry.add("spring.r2dbc.username", POSTGRES_QL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.r2dbc.password", POSTGRES_QL_CONTAINER::getPassword);

        dynamicPropertyRegistry.add("spring.flyway.url", () -> "jdbc:postgresql://"
                + POSTGRES_QL_CONTAINER.getHost() + ":" + POSTGRES_QL_CONTAINER.getFirstMappedPort()
                + "/" + POSTGRES_QL_CONTAINER.getDatabaseName());
        dynamicPropertyRegistry.add("spring.flyway.user", POSTGRES_QL_CONTAINER::getUsername);
        dynamicPropertyRegistry.add("spring.flyway.password", POSTGRES_QL_CONTAINER::getPassword);
    }
}

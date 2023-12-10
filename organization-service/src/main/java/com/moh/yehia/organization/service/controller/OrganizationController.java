package com.moh.yehia.organization.service.controller;

import com.moh.yehia.organization.service.model.OrganizationModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    @GetMapping("/{slug}")
    public Mono<OrganizationModel> findBySlug(@PathVariable("slug") String slug) {
        return Mono
                .just(new OrganizationModel(123L, "Our Organization", slug, LocalDateTime.now()));
    }
}

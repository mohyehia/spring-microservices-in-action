package com.moh.yehia.licence.service.controller;

import com.moh.yehia.licence.service.model.License;
import com.moh.yehia.licence.service.service.design.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/licences")
@RequiredArgsConstructor
public class LicenseController {
    private final LicenseService licenseService;

    @GetMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<License> findOne(@PathVariable("organizationId") String organizationId,
                                 @PathVariable("licenseId") long licenceId) {
        License license = licenseService.findOne(organizationId, licenceId);

        license.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .findOne(organizationId, licenceId)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .save(organizationId, license)).withRel(LinkRelation.of("saveLicense")),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .update(organizationId, license)).withRel(LinkRelation.of("updateLicense")),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .delete(organizationId, licenceId)).withRel(LinkRelation.of("deleteLicense"))
        );

        return Mono.just(license);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<License> save(@PathVariable("organizationId") String organizationId,
                              @RequestBody License license) {
        license = licenseService.save(organizationId, license);

        license.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .save(organizationId, license)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .findOne(organizationId, license.getId())).withRel(LinkRelation.of("findLicense")),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .update(organizationId, license)).withRel(LinkRelation.of("updateLicense")),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .delete(organizationId, license.getId())).withRel(LinkRelation.of("deleteLicense"))
        );

        return Mono.just(license);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<License> update(@PathVariable("organizationId") String organizationId,
                                @RequestBody License license) {
        license = licenseService.update(organizationId, license);

        license.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .update(organizationId, license)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .findOne(organizationId, license.getId())).withRel(LinkRelation.of("findLicense")),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .save(organizationId, license)).withRel(LinkRelation.of("saveLicense")),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                        .delete(organizationId, license.getId())).withRel(LinkRelation.of("deleteLicense"))
        );

        return Mono.just(license);
    }

    @DeleteMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("organizationId") String organizationId,
                             @PathVariable("licenseId") long licenceId) {
        licenseService.delete(organizationId, licenceId);
        return Mono.empty();
    }
}

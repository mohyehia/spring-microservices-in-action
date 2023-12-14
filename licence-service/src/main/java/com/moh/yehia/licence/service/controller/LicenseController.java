package com.moh.yehia.licence.service.controller;

import com.moh.yehia.licence.service.constant.AppConstants;
import com.moh.yehia.licence.service.mapper.LicenseMapper;
import com.moh.yehia.licence.service.model.License;
import com.moh.yehia.licence.service.model.LicenseDTO;
import com.moh.yehia.licence.service.model.LicenseModel;
import com.moh.yehia.licence.service.service.design.LicenseService;
import com.moh.yehia.licence.service.service.design.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/organizations/{organizationId}/licences")
@RequiredArgsConstructor
@Log4j2
public class LicenseController {
    private final LicenseService licenseService;
    private final LicenseMapper licenseMapper;
    private final OrganizationService organizationService;

    @GetMapping
    public Flux<LicenseModel> findAll(@PathVariable("organizationId") String organizationId) {
        return organizationService.findOne(organizationId)
                .flatMapMany(organizationDTO -> licenseService.findByOrganizationId(organizationDTO.slug()).map(license -> {
                    LicenseModel licenseModel = licenseMapper.mapToLicenceModel(license);
                    licenseModel.add(
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .findOne(organizationId, licenseModel.getId())).withRel(LinkRelation.of(AppConstants.WEB_LINK_FIND_LICENSE)),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .save(organizationId, populateLicenseDTO(license))).withRel(LinkRelation.of(AppConstants.WEB_LINK_SAVE_LICENSE)),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .update(organizationId, license.getId(), populateLicenseDTO(license))).withRel(LinkRelation.of(AppConstants.WEB_LINK_UPDATE_LICENSE)),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .delete(organizationId, license.getId())).withRel(LinkRelation.of(AppConstants.WEB_LINK_DELETE_LICENSE)));
                    return licenseModel;
                })).switchIfEmpty(Flux.empty());
    }

    @GetMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LicenseModel> findOne(@PathVariable("organizationId") String organizationId,
                                      @PathVariable("licenseId") String licenceId) {
        return organizationService.findOne(organizationId)
                .flatMap(organizationDTO -> licenseService.findOne(organizationDTO.slug(), licenceId).map(license -> {
                    LicenseModel licenseModel = licenseMapper.mapToLicenceModel(license);
                    licenseModel.add(
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .findOne(organizationId, licenceId)).withSelfRel(),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .save(organizationId, populateLicenseDTO(license))).withRel(LinkRelation.of(AppConstants.WEB_LINK_SAVE_LICENSE)),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .update(organizationId, licenceId, populateLicenseDTO(license))).withRel(LinkRelation.of(AppConstants.WEB_LINK_UPDATE_LICENSE)),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .delete(organizationId, license.getId())).withRel(LinkRelation.of(AppConstants.WEB_LINK_DELETE_LICENSE)));
                    return licenseModel;
                })).switchIfEmpty(Mono.empty());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LicenseModel> save(@PathVariable("organizationId") String organizationId,
                                   @RequestBody LicenseDTO licenseDTO) {
        return organizationService.findOne(organizationId)
                .flatMap(organizationDTO -> licenseService.save(organizationDTO.slug(), licenseDTO).map(license -> {
                    log.info("organizationDTO =>{}", organizationDTO.toString());
                    LicenseModel licenseModel = licenseMapper.mapToLicenceModel(license);
                    licenseModel.add(
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .save(organizationId, populateLicenseDTO(license))).withSelfRel(),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .findOne(organizationId, license.getId())).withRel(LinkRelation.of(AppConstants.WEB_LINK_FIND_LICENSE)),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .update(organizationId, license.getId(), populateLicenseDTO(license))).withRel(LinkRelation.of(AppConstants.WEB_LINK_UPDATE_LICENSE)),
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                                    .delete(organizationId, license.getId())).withRel(LinkRelation.of(AppConstants.WEB_LINK_DELETE_LICENSE))
                    );
                    return licenseModel;
                }).switchIfEmpty(Mono.empty()));
    }

    @PutMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<LicenseModel> update(@PathVariable("organizationId") String organizationId,
                                     @PathVariable("licenseId") String licenseId,
                                     @RequestBody LicenseDTO licenseDTO) {
        return licenseService.update(organizationId, licenseId, licenseDTO).map(license -> {
            LicenseModel licenseModel = licenseMapper.mapToLicenceModel(license);
            licenseModel.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                            .update(organizationId, licenseId, populateLicenseDTO(license))).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                            .findOne(organizationId, license.getId())).withRel(LinkRelation.of(AppConstants.WEB_LINK_FIND_LICENSE)),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                            .save(organizationId, populateLicenseDTO(license))).withRel(LinkRelation.of(AppConstants.WEB_LINK_SAVE_LICENSE)),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LicenseController.class)
                            .delete(organizationId, license.getId())).withRel(LinkRelation.of(AppConstants.WEB_LINK_DELETE_LICENSE))
            );
            return licenseModel;
        });
    }

    @DeleteMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable("organizationId") String organizationId,
                             @PathVariable("licenseId") String licenceId) {
        return licenseService.delete(organizationId, licenceId);
    }

    private LicenseDTO populateLicenseDTO(License license) {
        return new LicenseDTO(license.getOrganizationId(), license.getDescription(), license.getProductName(), license.getLicenceType());
    }
}

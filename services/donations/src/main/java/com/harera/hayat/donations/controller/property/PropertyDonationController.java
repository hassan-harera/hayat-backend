package com.harera.hayat.donations.controller.property;

import com.harera.hayat.donations.model.property.PropertyDonationRequest;
import com.harera.hayat.donations.model.property.PropertyDonationResponse;
import com.harera.hayat.donations.model.property.PropertyDonationUpdateRequest;
import com.harera.hayat.donations.service.property.PropertyDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/donations/property")
@Tag(name = "Property - Donation")
public class PropertyDonationController {

    private final PropertyDonationService propertyDonationService;

    public PropertyDonationController(PropertyDonationService donationService) {
        this.propertyDonationService = donationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create property donation", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok"),
            @ApiResponse(responseCode = "400", description = "error|Bad Request") })
    public ResponseEntity<PropertyDonationResponse> create(
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                    @RequestBody PropertyDonationRequest propertyDonationRequest) {
        return ok(propertyDonationService.create(propertyDonationRequest,
                        authorization.substring(7)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update property donation", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request") })
    public ResponseEntity<PropertyDonationResponse> update(
                    @RequestBody PropertyDonationUpdateRequest propertyDonationUpdateRequest,
                    @PathVariable("id") Long id) {
        return ok(propertyDonationService.update(id, propertyDonationUpdateRequest));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get property donation", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok"),
            @ApiResponse(responseCode = "404", description = "Not Found") })
    public ResponseEntity<PropertyDonationResponse> get(@PathVariable("id") Long id) {
        return ok(propertyDonationService.get(id));
    }

    @GetMapping
    @Operation(summary = "List", description = "list property donations", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok"),
            @ApiResponse(responseCode = "404", description = "Not Found") })
    public ResponseEntity<List<PropertyDonationResponse>> list(
                    @RequestParam(value = "page", defaultValue = "0") int page) {
        return ok(propertyDonationService.list(page));
    }

    @PutMapping("/{id}/upvote")
    @Operation(summary = "Upvote Blood Need", description = "Upvote Blood Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "Invalid request body"),
                            @ApiResponse(responseCode = "404",
                                            description = "Blood Need not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Property - Donation")
    public ResponseEntity<Void> upvoteBloodNeed(@PathVariable("id") Long id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        propertyDonationService.upvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/down-vote")
    @Operation(summary = "Down-vote Blood Need", description = "Down-vote Blood Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "Invalid request body"),
                            @ApiResponse(responseCode = "404",
                                            description = "Blood Need not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Property - Donation")
    public ResponseEntity<Void> downVoteBloodNeed(@PathVariable("id") Long id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        propertyDonationService.downvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }
}

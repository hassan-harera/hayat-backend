package com.harera.hayat.donations.controller.medicine;

import com.harera.hayat.donations.model.medicine.MedicineDonationRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonationResponse;
import com.harera.hayat.donations.model.medicine.MedicineDonationUpdateRequest;
import com.harera.hayat.donations.service.medicine.MedicineDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donations/medicine")
@Tag(name = "Medicine - Donation")
public class MedicineDonationController {

    private final MedicineDonationService medicineDonationService;

    public MedicineDonationController(MedicineDonationService donationService) {
        this.medicineDonationService = donationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a medicine donations",
                    tags = "Medicine - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> create(
                    @RequestBody MedicineDonationRequest medicineDonationRequest,
                    @RequestHeader("Authorization") String authorization) {
        MedicineDonationResponse donationResponse = medicineDonationService
                        .create(medicineDonationRequest, authorization.substring(7));
        return ResponseEntity.ok(donationResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update a medicine donation",
                    tags = "Medicine - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> update(@PathVariable("id") Long id,
                    @RequestBody MedicineDonationUpdateRequest medicineDonationUpdateRequest) {
        return ResponseEntity.ok(medicineDonationService.update(id,
                        medicineDonationUpdateRequest));
    }

    @GetMapping
    @Operation(summary = "List", description = "List medicine donations",
                    tags = "Medicine - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<MedicineDonationResponse>> list(
                    @RequestParam(value = "page", defaultValue = "0") int page,
                    @RequestParam(value = "size", defaultValue = "10") int size) {
        List<MedicineDonationResponse> medicineDonationResponses =
                        medicineDonationService.list(size, page);
        return ResponseEntity.ok(medicineDonationResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a medicine donations",
                    tags = "Medicine - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(medicineDonationService.get(id));
    }

    @GetMapping("/results")
    @Operation(summary = "List", description = "Search medicine donations",
                    tags = "Medicine - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<MedicineDonationResponse>> search(
                    @RequestParam(value = "page", defaultValue = "1") int page,
                    @RequestParam(value = "q", defaultValue = "") String query) {
        List<MedicineDonationResponse> medicineDonationResponses =
                        medicineDonationService.search(query, page);
        return ResponseEntity.ok(medicineDonationResponses);
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Insert Image",
                    description = "Insert image for a medicine donation",
                    tags = "Medicine - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> insertImage(
                    @RequestPart(name = "file") MultipartFile file,
                    @PathVariable("id") Long id) {
        return ResponseEntity.ok(medicineDonationService.updateImage(id, file));
    }

    @PutMapping("/{id}/upvote")
    @Operation(summary = "Upvote Medicine Donation",
                    description = "Upvote Medicine Donation",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "Invalid request body"),
                            @ApiResponse(responseCode = "404",
                                            description = "Medicine Donation not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Medicine - Donation")
    public ResponseEntity<Void> upvoteMedicineDonation(@PathVariable("id") Long id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        medicineDonationService.upvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/down-vote")
    @Operation(summary = "Down-vote Medicine Donation",
                    description = "DownVote Medicine Donation",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "Invalid request body"),
                            @ApiResponse(responseCode = "404",
                                            description = "Medicine Donation not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Medicine - Donation")
    public ResponseEntity<Void> downVoteMedicineDonation(@PathVariable("id") Long id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        medicineDonationService.downvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }
}

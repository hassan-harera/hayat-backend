package com.harera.hayat.donations.controller.clothing;

import com.harera.hayat.donations.model.clothing.ClothingDonationRequest;
import com.harera.hayat.donations.model.clothing.ClothingDonationResponse;
import com.harera.hayat.donations.model.clothing.ClothingDonationUpdateRequest;
import com.harera.hayat.donations.service.clothing.ClothingDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donations/clothing")
@Tag(name = "Clothing - Donation")
public class ClothingDonationController {

    private final ClothingDonationService clothingDonationService;

    public ClothingDonationController(ClothingDonationService clothingDonationService) {
        this.clothingDonationService = clothingDonationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a clothing donation",
                    tags = "Clothing - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<ClothingDonationResponse> create(
                    @RequestBody ClothingDonationRequest clothingDonationRequest,
                    @RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(clothingDonationService.create(clothingDonationRequest,
                        authorization.substring(7)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update a clothing donation",
                    tags = "Clothing - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<ClothingDonationResponse> update(@PathVariable("id") Long id,
                    @RequestBody ClothingDonationUpdateRequest clothingDonationUpdateRequest) {
        return ResponseEntity.ok(clothingDonationService.update(id,
                        clothingDonationUpdateRequest));
    }

    @GetMapping
    @Operation(summary = "List", description = "List clothing donations",
                    tags = "Clothing - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<ClothingDonationResponse>> list(
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        List<ClothingDonationResponse> clothingDonationResponses =
                        clothingDonationService.list(page);
        return ResponseEntity.ok(clothingDonationResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a clothing donations",
                    tags = "Clothing - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<ClothingDonationResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clothingDonationService.get(id));
    }

    @GetMapping("/results")
    @Operation(summary = "List", description = "Search clothing donations",
                    tags = "Clothing-Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<ClothingDonationResponse>> search(
                    @RequestParam(value = "page", defaultValue = "1") int page,
                    @RequestParam(value = "q", defaultValue = "") String query) {
        List<ClothingDonationResponse> clothingDonationResponses =
                        clothingDonationService.search(query, page);
        return ResponseEntity.ok(clothingDonationResponses);
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Insert Image",
                    description = "Insert image for a clothing donation",
                    tags = "Clothing - Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<ClothingDonationResponse> insertImage(
                    @RequestPart(name = "file") MultipartFile file,
                    @PathVariable("id") Long id) {
        return ResponseEntity.ok(clothingDonationService.updateImage(id, file));
    }

    @PutMapping("/{id}/upvote")
    @Operation(summary = "Upvote Clothing Donation",
                    description = "Upvote Clothing Donation",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "Invalid request body"),
                            @ApiResponse(responseCode = "404",
                                            description = "Clothing Donation not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Clothing - Donation")
    public ResponseEntity<Void> upvoteClothingDonation(@PathVariable("id") Long id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        clothingDonationService.upvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/down-vote")
    @Operation(summary = "Down-vote Clothing Donation",
                    description = "Down-vote Clothing Donation",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "Invalid request body"),
                            @ApiResponse(responseCode = "404",
                                            description = "Clothing Donation not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Clothing - Donation")
    public ResponseEntity<Void> downVoteClothingDonation(@PathVariable("id") Long id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        clothingDonationService.downvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }
}

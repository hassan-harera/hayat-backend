package com.harera.hayat.donations.controller.clothing;

import com.harera.hayat.donations.model.clothing.ClothingDonationRequest;
import com.harera.hayat.donations.model.clothing.ClothingDonationResponse;
import com.harera.hayat.donations.model.clothing.ClothingDonationUpdateRequest;
import com.harera.hayat.donations.service.clothing.ClothingDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donations/clothing")
@Tag(name = "Clothing-Donation")
public class ClothingDonationController {

    private final ClothingDonationService clothingDonationService;

    public ClothingDonationController(ClothingDonationService clothingDonationService) {
        this.clothingDonationService = clothingDonationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a clothing donation",
                    tags = "Clothing-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<ClothingDonationResponse> create(
                    @RequestBody ClothingDonationRequest clothingDonationRequest) {
        return ResponseEntity.ok(clothingDonationService.create(clothingDonationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update a clothing donation",
                    tags = "Clothing-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<ClothingDonationResponse> update(@PathVariable("id") Long id,
                    @RequestBody ClothingDonationUpdateRequest clothingDonationUpdateRequest) {
        return ResponseEntity
                        .ok(clothingDonationService.update(id, clothingDonationUpdateRequest));
    }

    @GetMapping
    @Operation(summary = "List", description = "List clothing donations",
                    tags = "Clothing-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<ClothingDonationResponse>> list(
                    @RequestParam(value = "page", defaultValue = "0") int page,
                    @RequestParam(value = "size", defaultValue = "10") int size) {
        List<ClothingDonationResponse> clothingDonationResponses =
                        clothingDonationService.list(size, page);
        return ResponseEntity.ok(clothingDonationResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a clothing donations",
                    tags = "Clothing-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<ClothingDonationResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(clothingDonationService.get(id));
    }
}

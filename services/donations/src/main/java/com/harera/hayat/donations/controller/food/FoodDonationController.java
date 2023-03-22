package com.harera.hayat.donations.controller.food;

import com.harera.hayat.donations.model.food.FoodDonationRequest;
import com.harera.hayat.donations.model.food.FoodDonationResponse;
import com.harera.hayat.donations.model.food.FoodDonationUpdateRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonationResponse;
import com.harera.hayat.donations.service.food.FoodDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donations/food")
@Tag(name = "Food-Donation")
public class FoodDonationController {

    private final FoodDonationService foodDonationService;

    public FoodDonationController(FoodDonationService foodDonationService) {
        this.foodDonationService = foodDonationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a food donation",
                    tags = "Food-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<FoodDonationResponse> create(
                    @RequestBody FoodDonationRequest foodDonationRequest) {
        return ResponseEntity.ok(foodDonationService.create(foodDonationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update a food donation",
                    tags = "Food-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<FoodDonationResponse> update(@PathVariable("id") Long id,
                    @RequestBody FoodDonationUpdateRequest foodDonationUpdateRequest) {
        return ResponseEntity
                        .ok(foodDonationService.update(id, foodDonationUpdateRequest));
    }

    @GetMapping
    @Operation(summary = "List", description = "List food donations",
                    tags = "Food-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<FoodDonationResponse>> list(
                    @RequestParam(value = "page", defaultValue = "0") int page,
                    @RequestParam(value = "size", defaultValue = "10") int size) {
        List<FoodDonationResponse> foodDonationResponses =
                        foodDonationService.list(size, page);
        return ResponseEntity.ok(foodDonationResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a food donations",
                    tags = "Food-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<FoodDonationResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(foodDonationService.get(id));
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Update Image", description = "Update the donation image",
                    tags = "Medicine-Donation",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<FoodDonationResponse> updateImage(
                    @RequestPart(name = "file") MultipartFile file,
                    @PathVariable("id") Long id) {
        return ResponseEntity.ok(foodDonationService.updateImage(id, file));
    }
}

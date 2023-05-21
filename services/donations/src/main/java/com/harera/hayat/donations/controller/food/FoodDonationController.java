package com.harera.hayat.donations.controller.food;

import com.harera.hayat.donations.model.food.FoodDonationRequest;
import com.harera.hayat.donations.model.food.FoodDonationResponse;
import com.harera.hayat.donations.model.food.FoodDonationUpdateRequest;
import com.harera.hayat.donations.service.food.FoodDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpHeaders;
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
                    @RequestBody FoodDonationRequest foodDonationRequest,
                    @RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(foodDonationService.create(foodDonationRequest,
                        authorization.substring(7)));
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
            tags = "Needs - Blood")
    public ResponseEntity<Void> upvoteBloodNeed(
            @PathVariable("id") Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        foodDonationService.upvote(id, authorization);
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
            tags = "Needs - Blood")
    public ResponseEntity<Void> downVoteBloodNeed(
            @PathVariable("id") Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        foodDonationService.downvote(id, authorization);
        return ResponseEntity.ok().build();
    }
}

package com.harera.hayat.donations.controller.medicine;

import com.harera.hayat.donations.model.medicine.MedicineDonationResponse;
import com.harera.hayat.donations.model.medicine.MedicineDonationUpdateRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonationRequest;
import com.harera.hayat.donations.model.medicine.MedicineDonationResponse;
import com.harera.hayat.donations.service.medicine.MedicineDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donations/medicine")
@Tag(name = "Medicine-Donation")
public class MedicineDonationController {

    private final MedicineDonationService medicineDonationService;

    public MedicineDonationController(MedicineDonationService donationService) {
        this.medicineDonationService = donationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a medicine donations",
                    tags = "Medicine-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> create(
                    @RequestBody MedicineDonationRequest medicineDonationRequest) {
        MedicineDonationResponse donationResponse =
                medicineDonationService.create(medicineDonationRequest);
        return ResponseEntity.ok(donationResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update a medicine donation",
            tags = "Medicine-Donation", responses = @ApiResponse(responseCode = "200",
            description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> update(@PathVariable("id") Long id,
                                                       @RequestBody MedicineDonationUpdateRequest medicineDonationUpdateRequest) {
        return ResponseEntity
                .ok(medicineDonationService.update(id, medicineDonationUpdateRequest));
    }

    @GetMapping
    @Operation(summary = "List", description = "List medicine donations",
            tags = "Medicine-Donation", responses = @ApiResponse(responseCode = "200",
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
            tags = "Medicine-Donation", responses = @ApiResponse(responseCode = "200",
            description = "success|Ok"))
    public ResponseEntity<MedicineDonationResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(medicineDonationService.get(id));
    }
}

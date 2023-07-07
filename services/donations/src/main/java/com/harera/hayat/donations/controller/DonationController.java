package com.harera.hayat.donations.controller;

import com.harera.hayat.donations.model.DonationResponse;
import com.harera.hayat.donations.service.DonationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Donations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/donations")
public class DonationController {

    private final DonationsService donationsService;

    @GetMapping("/results")
    @Operation(summary = "Search", description = "Search donations", tags = "Donations",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<DonationResponse>> search(
                    @RequestParam(value = "q", defaultValue = "") String query,
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        List<DonationResponse> donationResponseList =
                        donationsService.search(query, page);
        return ResponseEntity.ok(donationResponseList);
    }

    @PutMapping("/receive")
    @Operation(summary = "Receive", description = "Receive donation", tags = "Donations",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity receive(@RequestParam(value = "qr_code") String qrCode) {
        donationsService.receive(qrCode);
        return ResponseEntity.ok().build();
    }
}

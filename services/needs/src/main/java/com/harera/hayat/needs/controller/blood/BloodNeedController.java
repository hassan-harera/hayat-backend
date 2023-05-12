package com.harera.hayat.needs.controller.blood;

import com.harera.hayat.needs.model.blood.BloodNeedRequest;
import com.harera.hayat.needs.model.blood.BloodNeedResponse;
import com.harera.hayat.needs.service.blood.BloodNeedService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/needs/blood")
public class BloodNeedController {

    private final BloodNeedService bloodNeedService;

    @Autowired
    public BloodNeedController(BloodNeedService bloodNeedService) {
        this.bloodNeedService = bloodNeedService;
    }

    @PostMapping
    @Operation(summary = "Create Blood Need", description = "Create Blood Need")
    public ResponseEntity<BloodNeedResponse> createBloodNeed(
                    @RequestBody BloodNeedRequest bloodNeedRequest,
                    @RequestHeader("Authorization") String authorization) {
        BloodNeedResponse bloodNeedResponse =
                        bloodNeedService.create(bloodNeedRequest, authorization);
        return ResponseEntity.ok(bloodNeedResponse);
    }
}

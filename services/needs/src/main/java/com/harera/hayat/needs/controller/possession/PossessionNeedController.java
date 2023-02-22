package com.harera.hayat.needs.controller.possession;

import com.harera.hayat.needs.model.possession.PossessionNeedRequest;
import com.harera.hayat.needs.model.possession.PossessionNeedResponse;
import com.harera.hayat.needs.service.possession.PossessionNeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/needs/possession")
public class PossessionNeedController {

    private final PossessionNeedService possessionNeedService;

    public PossessionNeedController(PossessionNeedService possessionNeedService) {
        this.possessionNeedService = possessionNeedService;
    }

    @Operation(summary = "Create", description = "Create possession need", tags = "Possession - Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "BadRequest") })
    @PostMapping
    public ResponseEntity<PossessionNeedResponse> create(
                    @RequestBody PossessionNeedRequest possessionNeedRequest) {
        return ok(possessionNeedService.create(possessionNeedRequest));
    }
}

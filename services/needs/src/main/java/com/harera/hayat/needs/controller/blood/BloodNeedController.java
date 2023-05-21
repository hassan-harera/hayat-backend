package com.harera.hayat.needs.controller.blood;

import com.harera.hayat.needs.model.blood.BloodNeedRequest;
import com.harera.hayat.needs.model.blood.BloodNeedResponse;
import com.harera.hayat.needs.service.blood.BloodNeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Needs - Blood")
@RestController
@RequestMapping("/api/v1/needs/blood")
public class BloodNeedController {

    private final BloodNeedService bloodNeedService;

    @Autowired
    public BloodNeedController(BloodNeedService bloodNeedService) {
        this.bloodNeedService = bloodNeedService;
    }

    @PostMapping
    @Operation(summary = "Create Blood Need", description = "Create Blood Need",
                    tags = "Needs - Blood")
    public ResponseEntity<BloodNeedResponse> createBloodNeed(
                    @RequestBody BloodNeedRequest bloodNeedRequest,
                    @RequestHeader("Authorization") String authorization) {
        BloodNeedResponse bloodNeedResponse =
                        bloodNeedService.create(bloodNeedRequest, authorization);
        return ResponseEntity.ok(bloodNeedResponse);
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
                    @PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        bloodNeedService.upvote(id, authorization);
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
                    @PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        bloodNeedService.downvote(id, authorization);
        return ResponseEntity.ok().build();
    }
}

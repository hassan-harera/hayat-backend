package com.harera.hayat.needs.controller.possession;

import com.harera.hayat.needs.model.possession.PossessionNeedRequest;
import com.harera.hayat.needs.model.possession.PossessionNeedResponse;
import com.harera.hayat.needs.service.possession.PossessionNeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/needs/possession")
public class PossessionNeedController {

    private final PossessionNeedService possessionNeedService;

    public PossessionNeedController(PossessionNeedService possessionNeedService) {
        this.possessionNeedService = possessionNeedService;
    }

    @Operation(summary = "Create", description = "Create possession need",
                    tags = "Possession - Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "BadRequest") })
    @PostMapping
    public ResponseEntity<PossessionNeedResponse> create(
                    @RequestBody PossessionNeedRequest possessionNeedRequest,
                    @RequestHeader("Authorization") String authorization) {
        return ok(possessionNeedService.create(possessionNeedRequest, authorization));
    }

    @PutMapping("/{id}/upvote")
    @Operation(summary = "Upvote Book Need", description = "Upvote Book Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "404",
                                            description = "Book Need not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Possession - Need")
    public ResponseEntity<Void> upvoteBloodNeed(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        possessionNeedService.upvote(id, authorization);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/down-vote")
    @Operation(summary = "Down-vote Blood Need", description = "Down-vote Blood Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "404",
                                            description = "Book Need not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Possession - Need")
    public ResponseEntity<Void> downVoteBloodNeed(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        possessionNeedService.downvote(id, authorization);
        return ResponseEntity.ok().build();
    }
}

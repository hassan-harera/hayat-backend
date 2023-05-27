package com.harera.hayat.needs.controller.blood;

import com.harera.hayat.needs.model.blood.BloodNeedRequest;
import com.harera.hayat.needs.model.blood.BloodNeedResponse;
import com.harera.hayat.needs.model.blood.BloodNeedUpdateRequest;
import com.harera.hayat.needs.service.blood.BloodNeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Blood - Needs")
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
                    tags = "Blood - Needs")
    public ResponseEntity<BloodNeedResponse> createBloodNeed(
                    @RequestBody BloodNeedRequest bloodNeedRequest,
                    @RequestHeader("Authorization") String authorization) {
        BloodNeedResponse bloodNeedResponse = bloodNeedService.create(bloodNeedRequest,
                        authorization.substring(7));
        return ResponseEntity.ok(bloodNeedResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update a blood need",
                    tags = "Blood-Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<BloodNeedResponse> update(@PathVariable("id") String id,
                    @RequestBody BloodNeedUpdateRequest bloodNeedUpdateRequest) {
        return ResponseEntity.ok(bloodNeedService.update(id, bloodNeedUpdateRequest));
    }

    @GetMapping
    @Operation(summary = "List", description = "List blood needs", tags = "Blood-Need",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<BloodNeedResponse>> list(
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        List<BloodNeedResponse> bloodNeedResponses = bloodNeedService.list(page);
        return ResponseEntity.ok(bloodNeedResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a blood needs", tags = "Blood-Need",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<BloodNeedResponse> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(bloodNeedService.get(id));
    }

    @GetMapping("/results")
    @Operation(summary = "List", description = "Search blood needs", tags = "Blood-Need",
                    responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<BloodNeedResponse>> search(
                    @RequestParam(value = "page", defaultValue = "1") int page,
                    @RequestParam(value = "q", defaultValue = "") String query) {
        List<BloodNeedResponse> bloodNeedResponses = bloodNeedService.search(query, page);
        return ResponseEntity.ok(bloodNeedResponses);
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
                    tags = "Blood - Needs")
    public ResponseEntity<Void> upvoteBloodNeed(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        bloodNeedService.upvote(id, authorization.substring(7));
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
                    tags = "Blood - Needs")
    public ResponseEntity<Void> downVoteBloodNeed(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        bloodNeedService.downvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Update Image",
                    description = "Update the image of the blood need",
                    tags = "Blood - Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<BloodNeedResponse> insertImage(
                    @RequestPart(name = "file") MultipartFile file,
                    @PathVariable("id") String id) {
        return ResponseEntity.ok(bloodNeedService.updateImage(id, file));
    }
}

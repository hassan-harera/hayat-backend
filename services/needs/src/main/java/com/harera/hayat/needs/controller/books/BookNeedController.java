package com.harera.hayat.needs.controller.books;

import com.harera.hayat.needs.model.books.BookNeedRequest;
import com.harera.hayat.needs.model.books.BookNeedResponse;
import com.harera.hayat.needs.service.book.BookNeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Book - Need", description = "Book Need API")
@RestController
@RequestMapping("/api/v1/needs/book")
public class BookNeedController {

    private final BookNeedService bookNeedService;

    public BookNeedController(BookNeedService bookNeedService) {
        this.bookNeedService = bookNeedService;
    }

    @Operation(summary = "Create", description = "Create book need", tags = "Book - Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|Ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "BadRequest") })
    @PostMapping
    public ResponseEntity<BookNeedResponse> create(
                    @RequestBody BookNeedRequest bookNeedRequest,
                    @RequestHeader("Authorization") String authorization) {
        return ok(bookNeedService.create(bookNeedRequest, authorization));
    }

    @Operation(summary = "List", description = "List book needs", responses = {
            @ApiResponse(responseCode = "200", description = "success|ok"),
            @ApiResponse(responseCode = "401", description = "Unauthorized") })
    @GetMapping
    public ResponseEntity<List<BookNeedResponse>> list() {
        return ok(bookNeedService.list());
    }

    @PutMapping("/{id}/upvote")
    @Operation(summary = "Upvote Book Need", description = "Upvote Book Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "400",
                                            description = "Invalid request body"),
                            @ApiResponse(responseCode = "404",
                                            description = "Book Need not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Needs - Blood")
    public ResponseEntity<Void> upvoteBloodNeed(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        bookNeedService.upvote(id, authorization);
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
                                            description = "Book Need not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Needs - Blood")
    public ResponseEntity<Void> downVoteBloodNeed(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        bookNeedService.downvote(id, authorization);
        return ResponseEntity.ok().build();
    }
}

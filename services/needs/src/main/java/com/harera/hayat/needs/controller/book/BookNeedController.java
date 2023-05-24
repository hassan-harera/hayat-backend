package com.harera.hayat.needs.controller.book;

import com.harera.hayat.needs.model.book.BookNeedRequest;
import com.harera.hayat.needs.model.book.BookNeedResponse;
import com.harera.hayat.needs.service.book.BookNeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a book need by id",
                    tags = "Book - Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<BookNeedResponse> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(bookNeedService.get(id));
    }

    @GetMapping("/results")
    @Operation(summary = "List", description = "Search medicine needs",
                    tags = "Book - Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<BookNeedResponse>> search(
                    @RequestParam(value = "page", defaultValue = "1") int page,
                    @RequestParam(value = "q", defaultValue = "") String query) {
        List<BookNeedResponse> bookNeedResponses = bookNeedService.search(query, page);
        return ResponseEntity.ok(bookNeedResponses);
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
                    tags = "Book - Need")
    public ResponseEntity<Void> upvoteBloodNeed(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        bookNeedService.upvote(id, authorization.substring(7));
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
                    tags = "Book - Need")
    public ResponseEntity<Void> downVoteBloodNeed(@PathVariable("id") String id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        bookNeedService.downvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Insert Image",
                    description = "Update the image of the book need",
                    tags = "Book - Need", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<BookNeedResponse> insertImage(
                    @RequestPart(name = "file") MultipartFile file,
                    @PathVariable("id") String id) {
        return ResponseEntity.ok(bookNeedService.updateImage(id, file));
    }
}

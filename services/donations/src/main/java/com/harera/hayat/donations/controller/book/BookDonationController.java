package com.harera.hayat.donations.controller.book;

import com.harera.hayat.donations.model.book.BookDonationRequest;
import com.harera.hayat.donations.model.book.BookDonationResponse;
import com.harera.hayat.donations.model.book.BookDonationUpdateRequest;
import com.harera.hayat.donations.service.book.BookDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donations/book")
@Tag(name = "Book-Donation")
public class BookDonationController {

    private final BookDonationService bookDonationService;

    public BookDonationController(BookDonationService bookDonationService) {
        this.bookDonationService = bookDonationService;
    }

    @PostMapping
    @Operation(summary = "Create", description = "Create a book donation",
                    tags = "Book-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<BookDonationResponse> create(
                    @RequestBody BookDonationRequest bookDonationRequest,
                    @RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(bookDonationService.create(bookDonationRequest,
                        authorization.substring(7)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Update a book donation",
                    tags = "Book-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<BookDonationResponse> update(@PathVariable("id") Long id,
                    @RequestBody BookDonationUpdateRequest bookDonationUpdateRequest) {
        return ResponseEntity
                        .ok(bookDonationService.update(id, bookDonationUpdateRequest));
    }

    @GetMapping("/results")
    @Operation(summary = "Search", description = "Search book donations",
                    tags = "Book-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<BookDonationResponse>> search(
                    @RequestParam(value = "q", defaultValue = "") String query,
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        List<BookDonationResponse> bookDonationResponses =
                        bookDonationService.search(query, page);
        return ResponseEntity.ok(bookDonationResponses);
    }

    @GetMapping
    @Operation(summary = "List", description = "List book donations",
                    tags = "Book-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<BookDonationResponse>> list(
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        List<BookDonationResponse> bookDonationResponses = bookDonationService.list(page);
        return ResponseEntity.ok(bookDonationResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a book donations",
                    tags = "Book-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<BookDonationResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookDonationService.get(id));
    }

    @PutMapping("/{id}/upvote")
    @Operation(summary = "Upvote Blood Need", description = "Upvote Blood Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "404",
                                            description = "Blood Need not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Book-Donation")
    public ResponseEntity<Void> upvoteBloodNeed(@PathVariable("id") Long id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        bookDonationService.upvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/down-vote")
    @Operation(summary = "Down-vote Blood Need", description = "Down-vote Blood Need",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|ok"),
                            @ApiResponse(responseCode = "404",
                                            description = "Book Donation not found"),
                            @ApiResponse(responseCode = "401",
                                            description = "Unauthorized"), },
                    tags = "Book-Donation")
    public ResponseEntity<Void> downVoteBloodNeed(@PathVariable("id") Long id,
                    @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        bookDonationService.downvote(id, authorization.substring(7));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/images")
    @Operation(summary = "Update Image",
                    description = "Update the image of the book donation by its id",
                    tags = "Book-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<BookDonationResponse> insertImage(
                    @RequestPart(name = "file") MultipartFile file,
                    @PathVariable("id") Long id) {
        return ResponseEntity.ok(bookDonationService.updateImage(id, file));
    }
}

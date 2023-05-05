package com.harera.hayat.donations.controller.book;

import com.harera.hayat.donations.model.book.BookDonationRequest;
import com.harera.hayat.donations.model.book.BookDonationResponse;
import com.harera.hayat.donations.model.book.BookDonationUpdateRequest;
import com.harera.hayat.donations.service.book.BookDonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @Operation(summary = "List", description = "List book donations",
                    tags = "Book-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<List<BookDonationResponse>> list(
                    @RequestParam(value = "page", defaultValue = "0") int page,
                    @RequestParam(value = "size", defaultValue = "10") int size) {
        List<BookDonationResponse> bookDonationResponses =
                        bookDonationService.list(size, page);
        return ResponseEntity.ok(bookDonationResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get a book donations",
                    tags = "Book-Donation", responses = @ApiResponse(responseCode = "200",
                                    description = "success|Ok"))
    public ResponseEntity<BookDonationResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookDonationService.get(id));
    }
}

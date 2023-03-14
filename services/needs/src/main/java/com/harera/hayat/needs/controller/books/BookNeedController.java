package com.harera.hayat.needs.controller.books;

import com.harera.hayat.needs.model.books.BookNeedRequest;
import com.harera.hayat.needs.model.books.BookNeedResponse;
import com.harera.hayat.needs.service.book.BookNeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
                    @RequestBody BookNeedRequest bookNeedRequest) {
        return ok(bookNeedService.create(bookNeedRequest));
    }

    @Operation(summary = "List", description = "List book needs", responses = {
            @ApiResponse(responseCode = "200", description = "success|ok"),
            @ApiResponse(responseCode = "401", description = "Unauthorized") })
    @GetMapping
    public ResponseEntity<List<BookNeedResponse>> list() {
        return ok(bookNeedService.list());
    }
}

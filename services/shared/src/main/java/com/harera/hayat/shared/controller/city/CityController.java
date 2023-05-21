package com.harera.hayat.shared.controller.city;

import com.harera.hayat.framework.model.city.CityResponse;
import com.harera.hayat.framework.service.city.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/cities")
@Tag(name = "City")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("/search")
    @Operation(summary = "Search", description = "Search cities", tags = "City",
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<List<CityResponse>> search(
                    @RequestParam(name = "q", defaultValue = "") String query) {
        return ResponseEntity.status(HttpStatus.OK).body(cityService.search(query));
    }

    @GetMapping
    @Operation(summary = "List", description = "List cities", tags = "City", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok") })
    public ResponseEntity<List<CityResponse>> list() {
        return ok().body(cityService.list());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get city data", tags = "City",
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<CityResponse> get(@PathVariable("id") long id) {
        return ok(cityService.get(id));
    }
}

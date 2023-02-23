package com.harera.hayat.common.controller.city;

import java.util.List;

import com.harera.hayat.framework.model.city.CityResponse;
import com.harera.hayat.framework.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

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
                    @RequestParam(required = false,
                                    name = "arabic_name") String arabicName,
                    @RequestParam(name = "english_name",
                                    required = false) String englishName) {
        return ResponseEntity.status(HttpStatus.OK)
                        .body(cityService.search(arabicName, englishName));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get city data", tags = "City",
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<CityResponse> get(@PathVariable("id") long id) {
        return ResponseEntity.ok(cityService.get(id));
    }
}

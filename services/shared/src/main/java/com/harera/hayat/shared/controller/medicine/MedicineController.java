package com.harera.hayat.shared.controller.medicine;

import com.harera.hayat.framework.model.medicine.Medicine;
import com.harera.hayat.framework.service.medicine.MedicineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Medicine", description = "Medicines API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping
    @Operation(summary = "List", description = "List medicines", tags = { "Medicine" },
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<List<Medicine>> list(
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(medicineService.list(page));
    }

    @GetMapping("/search")
    @Operation(summary = "Search", description = "Search medicines",
                    tags = { "Medicine" },
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<List<Medicine>> search(
                    @RequestParam(value = "q", defaultValue = "") String query,
                    @RequestParam(value = "page", defaultValue = "1") int page) {
        return ResponseEntity.ok(medicineService.search(query, page));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get medicine units",
                    tags = { "Medicine Unit" },
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<Medicine> get(@PathVariable("id") long id) {
        return ResponseEntity.ok(medicineService.get(id));
    }
}

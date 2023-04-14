package com.harera.hayat.shared.controller.clothing;

import com.harera.hayat.framework.model.clothing.ClothingCategory;
import com.harera.hayat.framework.model.clothing.ClothingType;
import com.harera.hayat.framework.service.clothing.ClothingCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/clothing/categories")
public class ClothingCategoryController {

    private final ClothingCategoryService clothingCategoryService;

    @GetMapping
    @Operation(summary = "Get", description = "List clothing categories", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok") })
    public ResponseEntity<List<ClothingCategory>> list() {
        List<ClothingCategory> list = clothingCategoryService.list();
        return ResponseEntity.ok(list);
    }
}

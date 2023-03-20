package com.harera.hayat.shared.controller.clothing;

import com.harera.hayat.framework.model.ClothingSize;
import com.harera.hayat.framework.service.clothing.ClothingSizeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/api/v1/clothing/sizes")
public class ClothingSizeController {

    private final ClothingSizeService clothingConditionService;

    @GetMapping
    @Operation(summary = "Get", description = "List clothing sizes", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok") })
    public ResponseEntity<List<ClothingSize>> list() {
        List<ClothingSize> list = clothingConditionService.list();
        return ok(list);
    }
}

package com.harera.hayat.shared.controller.clothing;

import com.harera.hayat.framework.model.ClothingCondition;
import com.harera.hayat.framework.service.ClothingConditionService;
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
@RequestMapping("/api/v1/clothing/conditions")
public class ClothingConditionController {

    private final ClothingConditionService clothingConditionService;

    @GetMapping
    @Operation(summary = "Get", description = "List clothing conditions", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok") })
    public ResponseEntity<List<ClothingCondition>> list() {
        List<ClothingCondition> list = clothingConditionService.list();
        return ResponseEntity.ok(list);
    }
}

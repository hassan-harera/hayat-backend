package com.harera.hayat.shared.controller.clothing;

import com.harera.hayat.framework.model.clothing.ClothingSeason;
import com.harera.hayat.framework.service.clothing.ClothingSeasonService;
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
@RequestMapping("/api/v1/clothing/seasons")
public class ClothingSeasonController {

    private final ClothingSeasonService clothingConditionService;

    @GetMapping
    @Operation(summary = "Get", description = "List clothing seasons", responses = {
            @ApiResponse(responseCode = "200", description = "success|Ok") })
    public ResponseEntity<List<ClothingSeason>> list() {
        List<ClothingSeason> list = clothingConditionService.list();
        return ok(list);
    }
}

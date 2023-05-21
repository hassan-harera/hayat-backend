package com.harera.hayat.shared.controller.city;


import com.harera.hayat.framework.model.city.StateResponse;
import com.harera.hayat.framework.service.city.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/states")
@Tag(name = "State")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping
    @Operation(summary = "List", description = "list all states", tags = "State",
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<List<StateResponse>> list() {
        List<StateResponse> list = stateService.list();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get", description = "Get state data", tags = "State",
                    responses = { @ApiResponse(responseCode = "200",
                                    description = "success|Ok") })
    public ResponseEntity<StateResponse> get(@PathVariable("id") int id) {
        StateResponse stateResponse = stateService.get(id);
        return ResponseEntity.ok(stateResponse);
    }
}

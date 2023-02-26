package com.harera.hayat.authorization.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.harera.hayat.authorization.service.otp.OtpService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/otp")
@Tag(name = "Authentication")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @Operation(summary = "Request otp", description = "Request to send an otp to mobile number to signup or reset password",
                    tags = "Authentication",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|OK"),
                            @ApiResponse(responseCode = "403",
                                            description = "Forbidden"),
                            @ApiResponse(responseCode = "400",
                                            description = "Bad Request"),
                            @ApiResponse(responseCode = "404",
                                            description = "User's mobile Not Found") })
    @PostMapping("/request")
    public ResponseEntity<Void> request(@RequestParam(name = "mobile") String mobile) {
        otpService.request(mobile);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Validate otp", description = "Validate sent otp",
                    tags = "Authentication",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                            description = "success|OK") ,
                            @ApiResponse(responseCode = "400",
                                            description = "Bad Request") })
    @PostMapping("/validate")
    public ResponseEntity<Void> validate(
            @RequestParam(name = "otp") String otp,
            @RequestParam(name = "mobile") String mobile) {
        otpService.validate(mobile, otp);
        return ResponseEntity.ok().build();
    }
}

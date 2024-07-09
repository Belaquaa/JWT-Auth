package com.belaquaa.jwt.controllers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> allAccess() {
        logger.info("Accessing public content");
        return ResponseEntity.ok(new ApiResponse("Public Content."));
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> userAccess() {
        logger.info("Accessing user content");
        return ResponseEntity.ok(new ApiResponse("User Content."));
    }

    @GetMapping(value = "/mod", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ApiResponse> moderatorAccess() {
        logger.info("Accessing moderator content");
        return ResponseEntity.ok(new ApiResponse("Moderator Board."));
    }

    @GetMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> adminAccess() {
        logger.info("Accessing admin content");
        return ResponseEntity.ok(new ApiResponse("Admin Board."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        logger.error("Exception caught: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ApiResponse("An error occurred: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public record ApiResponse(String message) {
    }
}
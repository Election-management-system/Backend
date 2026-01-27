package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.backend.dtos.CandidateRegisterDTO;
import com.backend.services.CandidateService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidates")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/register")
    @Operation(description = "Register candidate for an election")
    @PreAuthorize("hasRole('VOTER')") // Only approved voters can register as candidates
    public ResponseEntity<?> registerCandidate(
            @Valid @RequestBody CandidateRegisterDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(candidateService.registerCandidate(dto));
    }
}

package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.VoteRequestDTO;
import com.backend.services.VoteService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/votes")
@CrossOrigin(origins = "http://localhost:3000")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    @Operation(description = "Cast vote")
    @PreAuthorize("hasRole('VOTER')") // Only authenticated voters can cast votes
    public ResponseEntity<?> castVote(
            @Valid @RequestBody VoteRequestDTO voteRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(voteService.castVote(voteRequest));
    }
}

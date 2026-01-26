package com.backend.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.response.ApiResponse;
import com.backend.service.VoterService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final VoterService voterService;

    public AdminController(VoterService voterService) {
        this.voterService = voterService;
    }

    @PutMapping("/verify-voter/{voterId}")
    public ApiResponse verifyVoter(@PathVariable Long voterId) {
        voterService.verifyVoter(voterId);
        return new ApiResponse("Voter verified successfully");
    }
}

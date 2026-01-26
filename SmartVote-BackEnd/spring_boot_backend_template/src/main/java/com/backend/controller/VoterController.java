package com.backend.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.request.VoteRequest;
import com.backend.dto.request.VoterRegisterRequest;
import com.backend.dto.response.ApiResponse;
import com.backend.service.VoteService;
import com.backend.service.VoterService;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    private final VoterService voterService;
    private final VoteService voteService;

    public VoterController(VoterService voterService, VoteService voteService) {
        this.voterService = voterService;
        this.voteService = voteService;
    }

    @PostMapping("/register")
    public ApiResponse register(@RequestBody VoterRegisterRequest request) {
        voterService.registerVoter(request);
        return new ApiResponse("Registration successful. Await admin verification.");
    }

    @PostMapping("/{voterId}/vote")
    public ApiResponse vote(@PathVariable Long voterId,
                            @RequestBody VoteRequest request) {
        voteService.castVote(voterId, request);
        return new ApiResponse("Vote cast successfully");
    }
}

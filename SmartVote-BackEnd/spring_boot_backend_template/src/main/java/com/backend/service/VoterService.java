package com.backend.service;

import com.backend.dto.request.VoterRegisterRequest;
import com.backend.entity.Voter;

public interface VoterService {
    Voter registerVoter(VoterRegisterRequest request);
    void verifyVoter(Long voterId);
}

package com.backend.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dto.request.VoterRegisterRequest;
import com.backend.entity.Voter;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.VoterRepository;
import com.backend.service.VoterService;

@Service
@Transactional
public class VoterServiceImpl implements VoterService {

    private final VoterRepository voterRepo;

    public VoterServiceImpl(VoterRepository voterRepo) {
        this.voterRepo = voterRepo;
    }

    public Voter registerVoter(VoterRegisterRequest request) {
        Voter voter = new Voter();
        voter.setFullName(request.getFullName());
        voter.setEmail(request.getEmail());
        voter.setStudentOrMemberId(request.getStudentOrMemberId());
        voter.setCourseOrDepartment(request.getCourseOrDepartment());
        return voterRepo.save(voter);
    }

    public void verifyVoter(Long voterId) {
        Voter voter = voterRepo.findById(voterId)
            .orElseThrow(() -> new ResourceNotFoundException("Voter not found"));
        voter.setEligible(true);
    }
}

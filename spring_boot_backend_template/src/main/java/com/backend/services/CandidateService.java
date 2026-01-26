package com.backend.services;

import java.util.List;

import com.backend.dtos.CandidateRegisterDTO;
import com.backend.dtos.CandidateResponseDTO;
import com.backend.dtos.PendingCandidateResponseDTO;

public interface CandidateService {

    String registerCandidate(CandidateRegisterDTO dto);
    
    List<CandidateResponseDTO> getAllCandidates();
    
    String approveCandidate(Long candidateId);
    
    List<PendingCandidateResponseDTO> getPendingCandidates();
}

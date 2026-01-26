package com.backend.services;

import java.util.List;

import com.backend.dtos.ElectionResultResponseDTO;
import com.backend.dtos.WinnerResponseDTO;

public interface ResultService {

  
    String declareElectionResults(Long electionId);

    List<ElectionResultResponseDTO> getStoredResults(Long electionId);
    
    WinnerResponseDTO declareWinner(Long electionId);
}



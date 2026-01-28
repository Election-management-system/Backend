package com.backend.services;


import java.util.List;

import com.backend.dtos.PendingVoterResponseDTO;
import com.backend.dtos.VoterRegisterDTO;
import com.backend.dtos.VoterResponseDTO;

import jakarta.validation.Valid;



public interface VoterService {

	List<VoterResponseDTO> getAllVoters();

	VoterResponseDTO registerVoter(VoterRegisterDTO voter);

	VoterResponseDTO getVoterById(Long voterId);

	String updateVoter(Long voterId, @Valid VoterRegisterDTO voter);
	
    String deleteVoter(Long voterId);
    
    String approveVoter(Long voterId);
    
    List<PendingVoterResponseDTO> getPendingVoters();
		
	

}


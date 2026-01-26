package com.backend.services;


import java.util.List;

import com.backend.dtos.VoterRegisterDTO;
import com.backend.dtos.VoterResponseDTO;
import com.backend.entities.Voter;

import jakarta.validation.Valid;



public interface VoterService {

	List<VoterResponseDTO> getAllVoters();

	String registerVoter(VoterRegisterDTO voter);

	VoterResponseDTO getVoterById(Long voterId);

	String updateVoter(Long voterId, @Valid VoterRegisterDTO voter);
	
    String deleteVoter(Long voterId);
		
	

}


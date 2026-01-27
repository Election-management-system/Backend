package com.backend.services;

import java.util.List;

import com.backend.dtos.ElectionCreateDTO;
import com.backend.dtos.ElectionResponseDTO;
import com.backend.entities.Election;

public interface ElectionService {

	List<ElectionResponseDTO> getUpcomingElections();

	List<ElectionResponseDTO> getAllElections();
	
	String createElection(ElectionCreateDTO dto);

	void autoCloseIfExpired(Election election);
	
	String activateElection(Long electionId);

}

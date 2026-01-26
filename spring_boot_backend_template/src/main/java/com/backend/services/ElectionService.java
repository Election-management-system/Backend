package com.backend.services;

import java.util.List;

import com.backend.dtos.ElectionResponseDTO;

public interface ElectionService {

	List<ElectionResponseDTO> getUpcomingElections();

	List<ElectionResponseDTO> getAllElections();

}

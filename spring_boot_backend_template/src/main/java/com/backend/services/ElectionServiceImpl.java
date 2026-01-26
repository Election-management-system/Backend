package com.backend.services;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dtos.ElectionResponseDTO;
import com.backend.repository.ElectionRepository;
import com.backend.entities.Election;


import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ElectionServiceImpl implements  ElectionService {

	@Autowired
	private  ElectionRepository electionRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	@Override
	public List<ElectionResponseDTO> getUpcomingElections() {

	    // 1. Get today's date
	    LocalDate today = LocalDate.now();

	    // 2. Fetch upcoming elections from DB
	    List<Election> elections =
	            electionRepository.findByElectionDateGreaterThanEqualAndIsactiveTrueOrderByElectionDateAsc(today);
	    
	    

	    // 3. Convert Entity → DTO
	    return elections.stream()
	            .map(election -> modelMapper.map(election, ElectionResponseDTO.class))
	            .toList();
	}



	@Override
	public List<ElectionResponseDTO> getAllElections() {
		 // 1. Fetch upcoming elections from DB
	    List<Election> elections =
	            electionRepository.findAll();
	    
	    // 2. Convert Entity → DTO
	    return elections.stream()
	            .map(election -> modelMapper.map(election, ElectionResponseDTO.class))
	            .toList();
	}

	

}

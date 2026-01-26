package com.backend.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dtos.ElectionResponseDTO;
import com.backend.services.ElectionService;

import io.swagger.v3.oas.annotations.Operation;



@RestController // to declare a spring bean - containing REST API end point provider
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:3000") // to set CORS policy on specific origins
@Validated
public class HomeController {
	
	
	@Autowired
	private ElectionService electionService;
	

	@GetMapping
	@Operation(description = "Fetching upcoming elections on HomePage")
	public ResponseEntity<?> getUpcomingElections() {

	    List<ElectionResponseDTO> electionList = electionService.getUpcomingElections();

	    if (electionList.isEmpty()) {
	        return ResponseEntity.noContent().build(); // 204
	    }

	    return ResponseEntity.ok(electionList); // 200
	}
	
	@GetMapping("/pastElections")
	@Operation(description = "Fetching all history of elections on HomePage")
	public ResponseEntity<?> getAllElections() {

	    List<ElectionResponseDTO> electionList = electionService.getAllElections();

	    if (electionList.isEmpty()) {
	        return ResponseEntity.noContent().build(); // 204
	    }

	    return ResponseEntity.ok(electionList); // 200
	}
	
	
	

}

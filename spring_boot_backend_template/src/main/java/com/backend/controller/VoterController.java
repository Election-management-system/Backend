package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.backend.dtos.VoterRegisterDTO;
import com.backend.dtos.VoterResponseDTO;
import com.backend.services.VoterService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController // to declare a spring bean - containing REST API end point provider
@RequestMapping("/voters")
@CrossOrigin(origins = "http://localhost:3000") // to set CORS policy on specific origins
@Validated
public class VoterController {

	@Autowired
	private VoterService voterService;

	@GetMapping
	@Operation(description = "Get All Voters")
	@PreAuthorize("hasRole('ADMIN')") // Only admin can view all voters
	public ResponseEntity<?> getAllVoters() {
		System.out.println("getting all the voters...");

		List<VoterResponseDTO> voters = voterService.getAllVoters();
		if (voters.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT) // SC 204
					.build();
		}
		return ResponseEntity.ok(voters);
	}

	@PostMapping
	@Operation(description = "Register voter")
	@PreAuthorize("permitAll()") // Public registration
	public ResponseEntity<?> registerVoter(@RequestBody @Valid VoterRegisterDTO voter) {

		return ResponseEntity.ok(voterService.registerVoter(voter));
	}

	@GetMapping("/{voterId}")
	@Operation(description = "Getting Voter Details by Voter Id")
	@PreAuthorize("hasRole('VOTER') or hasRole('ADMIN')") // Voter can view own details, admin can view any
	public ResponseEntity<?> getVoterByVoterId(@PathVariable Long voterId) {

		VoterResponseDTO newVoter = voterService.getVoterById(voterId);
		if (newVoter == null) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Voter not found with ID: " + voterId);
		}

		return ResponseEntity.ok(newVoter);
	}

	@PutMapping("/{voterId}")
	@Operation(description = "Update voter details")
	@PreAuthorize("hasRole('VOTER') or hasRole('ADMIN')") // Voter can update own, admin can update any
	public ResponseEntity<?> updateVoter(
			@PathVariable Long voterId,
			@Valid @RequestBody VoterRegisterDTO voter) {

		return ResponseEntity.ok(
				voterService.updateVoter(voterId, voter));

	}

	@DeleteMapping("/{voterId}")
	@Operation(description = "Soft delete voter")
	@PreAuthorize("hasRole('ADMIN')") // Only admin can delete voters
	public ResponseEntity<?> deleteVoter(@PathVariable Long voterId) {

		voterService.deleteVoter(voterId);
		return ResponseEntity.noContent().build();
	}

}

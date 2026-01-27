package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.backend.dtos.AdminRegisterDTO;
import com.backend.dtos.ElectionCreateDTO;
import com.backend.services.AdminService;
import com.backend.services.CandidateService;
import com.backend.services.ElectionService;
import com.backend.services.ResultService;
import com.backend.services.VoterService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class AdminController {

    @Autowired
    private ElectionService electionService;
    
    @Autowired
    private CandidateService candidateService;
    
    @Autowired
    private VoterService voterService;
    
    @Autowired
    private ResultService resultService;
    
    
    @Autowired
    private  AdminService adminService;
//    
//    public AdminController(AdminService adminService) {
//        this.adminService = adminService;
//    }
//    
//    // 🔐 ADMIN ONLY
//    @PostMapping("/register")
//    public ResponseEntity<?> registerAdmin(
//            @RequestBody AdminRegisterDTO dto) {
//
//        return ResponseEntity.ok(adminService.registerAdmin(dto));
//    }

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterDTO dto) {
        return ResponseEntity.ok(adminService.registerAdmin(dto));
    }


    
    @GetMapping("/candidates")
    public ResponseEntity<?> getAllCandidates() {

        return ResponseEntity.ok(
                candidateService.getAllCandidates()
        );
    }
    
    @GetMapping("/candidates/pending")
    public ResponseEntity<?> getPendingCandidates() {

        return ResponseEntity.ok(
                candidateService.getPendingCandidates()
        );
    }
    
    @PutMapping("/candidates/{candidateId}/approve")
    public ResponseEntity<?> approveCandidate(
            @PathVariable Long candidateId) {

        return ResponseEntity.ok(
                candidateService.approveCandidate(candidateId)
        );
    }
    
    @GetMapping("/voters/pending")
    public ResponseEntity<?> getPendingVoters() {

        return ResponseEntity.ok(
                voterService.getPendingVoters()
        );
    }
    
    @PutMapping("/voters/{voterId}/approve")
    public ResponseEntity<?> approveVoter(
            @PathVariable Long voterId) {

        return ResponseEntity.ok(
                voterService.approveVoter(voterId)
        );
    }

    @PostMapping("/elections")
    @Operation(description = "Admin creates a new election")
    public ResponseEntity<?> createElection(
            @Valid @RequestBody ElectionCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(electionService.createElection(dto));
    }
    
    @PutMapping("/elections/{electionId}/activate")
    public ResponseEntity<?> activateElection(
            @PathVariable Long electionId) {

        return ResponseEntity.ok(
                electionService.activateElection(electionId)
        );
    }
    
    @PostMapping("/elections/{electionId}/declare-winner")
    public ResponseEntity<?> declareWinner(
            @PathVariable Long electionId) {

        return ResponseEntity.ok(
                resultService.declareWinner(electionId)
        );
    }
    
    @PostMapping("/elections/{electionId}/declare-results")
    public ResponseEntity<?> declareResults(
            @PathVariable Long electionId) {

        return ResponseEntity.ok(
                resultService.declareElectionResults(electionId)
        );
    }

}

package com.backend.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequestDTO {

    @NotNull(message = "Voter ID is required")
    private Long voterId;

    @NotNull(message = "Candidate ID is required")
    private Long candidateId;

    @NotNull(message = "Election ID is required")
    private Long electionId;
}

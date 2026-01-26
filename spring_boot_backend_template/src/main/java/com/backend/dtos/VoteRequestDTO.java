package com.backend.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequestDTO {

    @NotNull
    private Long voterId;

    @NotNull
    private Long candidateId;

    @NotNull
    private Long electionId;
}

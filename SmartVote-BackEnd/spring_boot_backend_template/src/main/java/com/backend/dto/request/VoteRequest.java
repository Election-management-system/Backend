package com.backend.dto.request;

import lombok.Data;

@Data
public class VoteRequest {
    private Long electionId;
    private Long candidateId;
    private String postName;
}

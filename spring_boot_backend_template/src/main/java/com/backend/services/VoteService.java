package com.backend.services;

import com.backend.dtos.VoteRequestDTO;

public interface VoteService {

    String castVote(VoteRequestDTO dto);

}

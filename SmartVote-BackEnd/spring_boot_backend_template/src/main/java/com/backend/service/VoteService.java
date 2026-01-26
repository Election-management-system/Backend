package com.backend.service;

import com.backend.dto.request.VoteRequest;

public interface VoteService {

	void castVote(Long voterId, VoteRequest request);

}

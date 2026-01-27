package com.backend.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoterResponseDTO {

	// private Long voter_id;

	@JsonProperty("voter_id")
	private Long id;

	private String firstName;

	private String lastName;

	private String email;

	private LocalDate DOB;

	private String aadharCardNo;

	private String address;

	private String mobileNo;

	private boolean isApproved;

}

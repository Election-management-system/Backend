package com.backend.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoterRegisterDTO {

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate DOB;

	private String aadharCardNo;

	private String address;

	private String mobileNo;

}

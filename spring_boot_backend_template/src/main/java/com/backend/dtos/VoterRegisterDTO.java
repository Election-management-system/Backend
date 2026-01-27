package com.backend.dtos;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoterRegisterDTO {

	
	private String firstName;
	

	private String lastName;
	

	private String email;
	
	
	private String password;
	
    private LocalDate DOB;
	
	private String aadharCardNo;
	
	
	private String address;
	
	private String mobileNo;
	

}

package com.backend.dtos;

import jakarta.validation.constraints.Email;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
	
	@Email
	private String email;
	
	private String password; 

}

package com.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegisterDTO {

    private String firstName;
    
    private String lastName;
   
    private String email;
    private String password;
}

//{
//	  "firstName": "SmartVote",
//	  "lastName": "Admin",
//	  "email": "admin@smartvote.com",
//	  "password": "password"
//	}

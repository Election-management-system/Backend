package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController // to declare a spring bean - containing REST API end point provider
@RequestMapping("/voters")
@CrossOrigin(origins = "http://localhost:3000") // to set CORS policy on specific origins
@Validated
public class VoterController {
	
	//@Autowired
	//private VoterService voterService;
	
	

}

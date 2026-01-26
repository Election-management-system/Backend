package com.backend.entities;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "Voters")
@AttributeOverride(name = "id", column = @Column(name = "Voter_id"))
//Lombok annotations
@NoArgsConstructor
@Getter
@Setter

@ToString(callSuper = true)
public class Voter extends BaseEntity {
	
	
	@Column(name = "first_name", length = 50 , nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 50 , nullable = false)
	private String lastName;
	
	@Column(name = "email", length = 50 ,unique = true, nullable = false)
	private String email;
	
	@Column(name = "password", length = 250 , nullable = false)
	private String password;
	
	@Column(name = "birth_date", nullable = false)
	private LocalDate DOB;
	
	@Column(name = "aadharcard_no",unique = true,length = 20, nullable = false)
	private String aadharCardNo;
	
	@Column(name = "address",length = 100, nullable = false)
	private String address;
	
	@Column(name = "mobile_no",length = 20, nullable = false)
	private String mobileNo;
	
	@Column(name = "is_voted" , nullable = false )
	private boolean isVoted;
	
	@Column(name = "is_approved",nullable = false)
	private boolean isApproved;

	public Voter(String firstName, String lastName, String email, String password, LocalDate dOB, String aadharCardNo,
			String address, String mobileNo, boolean isVoted, boolean isApproved) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		DOB = dOB;
		this.aadharCardNo = aadharCardNo;
		this.address = address;
		this.mobileNo = mobileNo;
		this.isVoted = isVoted;
		this.isApproved = isApproved;
	}

	
	
	
	
	

}

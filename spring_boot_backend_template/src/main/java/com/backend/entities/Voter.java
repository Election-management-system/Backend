package com.backend.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Entity;
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
	
	@Column(name = "email", length = 50 , nullable = false)
	private String email;
	
	@Column(name = "password", length = 250 , nullable = false)
	private String password;
	
	@Column(name = "is_voted" , nullable = false )
	private boolean isVoted;

	public Voter(String firstName, String lastName, String email, String password, boolean isVoted) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isVoted = isVoted;
	}
	
	
	

}

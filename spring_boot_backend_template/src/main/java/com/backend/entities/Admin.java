package com.backend.entities;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Admin")
@AttributeOverride(name = "id", column = @Column(name = "admin_id"))
//Lombok annotations
@NoArgsConstructor
@Getter
@Setter

@ToString(callSuper = true)
public class Admin extends BaseEntity {


	@Column(name = "first_name", length = 50 , nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 50 , nullable = false)
	private String lastName;
	
	@Column(name = "email", length = 50 , nullable = false)
	private String email;
	
	@Column(name = "password", length = 250 , nullable = false)
	private String password;
	
	@Column(name = "role", nullable = false)
	private String role;

	
}

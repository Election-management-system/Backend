package com.backend.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "DemoTable")
@AttributeOverride(name = "id", column = @Column(name = "demo_id"))
//Lombok annotations
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class DemoTable  extends BaseEntity{

	@Column(name = "demo_entry", length = 50)
	private String DemoEntry;
	
}

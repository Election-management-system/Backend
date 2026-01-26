package com.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entities.Election;

public interface ElectionRepository extends JpaRepository<Election, Long> {

//	List<Election> findByElectionDateGreaterThanEqualAndaIsactiveTrueOrderByElectionDateAsc(LocalDate today);

	List<Election> findByElectionDateGreaterThanEqualAndIsactiveTrueOrderByElectionDateAsc(LocalDate today);

}

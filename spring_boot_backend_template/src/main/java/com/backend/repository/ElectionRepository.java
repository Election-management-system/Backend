package com.backend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.entities.Election;

public interface ElectionRepository extends JpaRepository<Election, Long> {

//	List<Election> findByElectionDateGreaterThanEqualAndaIsactiveTrueOrderByElectionDateAsc(LocalDate today);

	List<Election> findByElectionDateGreaterThanEqualOrderByElectionDateAsc(LocalDate today);
	
	 @Modifying
	    @Query(
	        value = """
	                UPDATE elections
	                SET is_active = 0
	                WHERE election_date < CURDATE()
	                  AND is_active = 1
	                """,
	        nativeQuery = true
	    )
	    int closeExpiredElections();

}

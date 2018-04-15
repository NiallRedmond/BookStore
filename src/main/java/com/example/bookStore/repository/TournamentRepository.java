package com.example.bookStore.repository;

import org.hibernate.mapping.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import com.example.bookStore.model.Tournament;
@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
	
	
}

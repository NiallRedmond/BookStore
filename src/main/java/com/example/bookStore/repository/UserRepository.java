package com.example.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.bookStore.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	//User findByName(String name);
	
	User findByEmail(String email);
}

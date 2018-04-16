package com.example.bookStore.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.bookStore.model.User;
import com.example.bookStore.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {

	@Autowired
	UserRepository userRepository;

	// Get All Users
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	// Create a new user
	@PostMapping("/users")
	public User createUsers(@Valid @RequestBody User user) {
	    return userRepository.save(user);
	}

	// Get a Single User
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
	    User user = userRepository.findOne(userId);
	    if(user == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(user);
	}


	// Delete a User
	@DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteNote(@PathVariable(value = "id") Long userId) {
	    User user = userRepository.findOne(userId);
	    if(user == null) {
	        return ResponseEntity.notFound().build();
	    }
	    userRepository.delete(user);
	    return ResponseEntity.ok().build();
	}
}


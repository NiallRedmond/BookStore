package com.example.bookStore.service;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.bookStore.dto.UserRegistrationDto;
import com.example.bookStore.model.User;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}
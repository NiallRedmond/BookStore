package com.example.bookStore.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookStore.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
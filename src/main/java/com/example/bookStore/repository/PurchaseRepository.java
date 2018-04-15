package com.example.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.bookStore.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}

package com.csc3402.lab.avr.repository;

import com.csc3402.lab.avr.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Additional custom query methods can be defined here if needed
}

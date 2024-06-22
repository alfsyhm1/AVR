package com.csc3402.lab.avr.repository;

import com.csc3402.lab.avr.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    // Additional custom query methods can be defined here if needed
}

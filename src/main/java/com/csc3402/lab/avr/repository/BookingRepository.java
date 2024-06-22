package com.csc3402.lab.avr.repository;

import com.csc3402.lab.avr.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    // Additional custom query methods can be defined here if needed
}

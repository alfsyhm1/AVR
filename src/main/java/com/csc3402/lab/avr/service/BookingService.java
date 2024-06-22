package com.csc3402.lab.avr.service;

import com.csc3402.lab.avr.model.Booking;

public interface BookingService {

    Booking createBooking(Booking booking);

    Booking getBookingById(Integer bookingId);

    Booking updateBooking(Booking booking);

    void deleteBooking(Integer bookingId);

    // Other methods as needed
}

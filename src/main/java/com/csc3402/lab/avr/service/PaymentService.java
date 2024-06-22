package com.csc3402.lab.avr.service;

import com.csc3402.lab.avr.model.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(Integer id);
    Payment savePayment(Payment payment);
    void deletePayment(Integer id);
}

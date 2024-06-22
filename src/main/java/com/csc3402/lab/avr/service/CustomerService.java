package com.csc3402.lab.avr.service;

import com.csc3402.lab.avr.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Optional<Customer> getCustomerById(Integer id);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Integer id);
}

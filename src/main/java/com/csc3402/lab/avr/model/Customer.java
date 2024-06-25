package com.csc3402.lab.avr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int custid;

    @NotBlank(message = "Full name is mandatory")
    private String fullName;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public Customer() {}

    public Customer(String fullName, String email, String password, Booking booking) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.booking = booking;
    }

    // Getters and setters
    public int getCustid() {
        return custid;
    }

    public void setCustid(int custid) {
        this.custid = custid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custid=" + custid +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", booking=" + booking +
                '}';
    }
}
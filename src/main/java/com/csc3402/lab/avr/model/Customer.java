package com.csc3402.lab.avr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int custid;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Customer() {}

    public Customer(String fullName, String email, String phone, Booking booking, Room room) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.booking = booking;
        this.room = room;
    }

    // Getters and setters
    public int getCustid() {
        return custid;
    }

    public void setCustid(int custid) {
        this.custid = custid;
    }

    public String getfullName() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Room getRoom(){
        return room;
    }

    public void setRoom(Room room){
        this.room = room;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custid=" + custid +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", booking=" + booking +
                ", room=" + room +
                '}';
    }
}
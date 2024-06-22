package com.csc3402.lab.avr.controller;

import com.csc3402.lab.avr.model.*;
import com.csc3402.lab.avr.repository.CustomerRepository;
import com.csc3402.lab.avr.repository.PaymentRepository;
import com.csc3402.lab.avr.repository.RoomRepository;
import com.csc3402.lab.avr.repository.BookingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "index";
    }

    @GetMapping("/customers/list")
    public String showCustomerList(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "list-customer";
    }

    @GetMapping("/customers/signup")
    public String showSignUpForm(Customer customer) {
        return "register";
    }

    @PostMapping("/customers/add")
    public String addCustomer(@Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        customerRepository.save(customer);
        return "redirect:/customers/list";
    }

    @GetMapping("/customers/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return "update-customer";
    }

    @PostMapping("/customers/update/{id}")
    public String updateCustomer(@PathVariable("id") long id, @Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            customer.setCustid((int) id);
            return "update-customer";
        }
        customerRepository.save(customer);
        return "redirect:/customers/list";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        customerRepository.delete(customer);
        return "redirect:/customers/list";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("rooms", roomRepository.findAll()); // For room selection in checkout form
        return "checkout"; // Ensure this matches the template name
    }

    @PostMapping("/checkout")
    public String addPayment(@Valid Payment payment, BindingResult result, Model model,
                             @RequestParam("selectedRoom") String selectedRoom,
                             @RequestParam("checkin") String checkin,
                             @RequestParam("checkout") String checkout) {
        if (result.hasErrors()) {
            // Handle validation errors
            return "checkout";
        }

        // Fetch the room based on selectedRoom (omitted for brevity)

        // Calculate total price based on room and dates (omitted for brevity)

        // Create a new booking
        Booking booking = new Booking();
        booking.setStart(Date.from(LocalDate.parse(checkin).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        booking.setEndDate(Date.from(LocalDate.parse(checkout).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        booking.setBookDate(new Date());
        booking.setNotes("Booking notes");
        booking.setStatus("Confirmed");
        booking = bookingRepository.save(booking);

        // Set the booking for the payment
        payment.setBooking(booking);

        // Save the payment
        paymentRepository.save(payment);

        // Add necessary attributes to display on checkout.html if needed
        model.addAttribute("booking", booking);
        model.addAttribute("totalPrice", payment.getTotalPrice()); // Assuming you need to display total price on checkout.html

        // Return the checkout.html view
        return "checkout";
    }



    @GetMapping("/confirmation")
    public String confirmation(@RequestParam Integer bookingId, Model model) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid booking Id:" + bookingId));
        model.addAttribute("booking", booking);
        return "bookingconfirmation";
    }
}

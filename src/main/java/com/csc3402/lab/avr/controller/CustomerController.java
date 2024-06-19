package com.csc3402.lab.avr.controller;

import com.csc3402.lab.avr.model.Customer;
import com.csc3402.lab.avr.repository.CustomerRepository;
import com.csc3402.lab.avr.service.BookingService;
import com.csc3402.lab.avr.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final BookingService bookingService;
    private final RoomService roomService;
    private final CustomerRepository customerRepository;

    public CustomerController(BookingService bookingService, CustomerRepository customerRepository, RoomService roomService) {
        this.bookingService = bookingService;
        this.customerRepository = customerRepository;
        this.roomService = roomService;
    }



    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "list-customer";
    }

    @GetMapping("signup")
    public String showSignUpForm(Customer customer, Model model){
        model.addAttribute("bookings", bookingService.listAllBookings());
        model.addAttribute("rooms", roomService.listAllRooms());
        return "add-customer";
    }

    @PostMapping("add")
    public String addCustomer(@Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-customer";
        }

        customerRepository.save(customer);
        return "redirect:list";
    }

    @GetMapping("update")
    public String showUpdateMainForm(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "choose-customer-to-update";
    }
    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));

        model.addAttribute("customer", customer);
        model.addAttribute("bookings", bookingService.listAllBookings());
        model.addAttribute("rooms", roomService.listAllRooms());
        return "update-customer";
    }


    @PostMapping("update/{id}")
    public String updateStaff(@PathVariable("id") long id, @Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            customer.setCustid((int) id);
            return "index";
        }

        customerRepository.save(customer);
        model.addAttribute("customers", customerRepository.findAll());

        return "list-customer";
    }

    @GetMapping("delete")
    public String showDeleteMainForm(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "choose-customer-to-delete";
    }



    @GetMapping("delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById((int) id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));

        customerRepository.delete(customer);
        model.addAttribute("customers", customerRepository.findAll());

        return "list-customer";
    }




}
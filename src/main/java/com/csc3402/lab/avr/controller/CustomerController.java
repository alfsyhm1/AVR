package com.csc3402.lab.avr.controller;

import com.csc3402.lab.avr.model.Booking;
import com.csc3402.lab.avr.model.Payment;
import com.csc3402.lab.avr.model.Room;
import com.csc3402.lab.avr.repository.BookingRepository;
import com.csc3402.lab.avr.repository.PaymentRepository;
import com.csc3402.lab.avr.repository.RoomRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private RoomRepository roomRepository;

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

    @GetMapping("/checkout")
    public String checkout(@RequestParam("selectedRoom") String selectedRoom,
                           @RequestParam("checkin") String checkin,
                           @RequestParam("checkout") String checkout,
                           @RequestParam("counterValueAdult") int counterValueAdult,
                           @RequestParam("counterValueChild") int counterValueChild,
                           Model model) {
        model.addAttribute("selectedRoom", selectedRoom);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("counterValueAdult", counterValueAdult);
        model.addAttribute("counterValueChild", counterValueChild);
        model.addAttribute("payment", new Payment());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String addPayment(@Valid Payment payment, BindingResult result,
                             @RequestParam("selectedRoom") String selectedRoom,
                             @RequestParam("checkin") String checkin,
                             @RequestParam("checkout") String checkout,
                             Model model) {
        if (result.hasErrors()) {
            return "checkout";
        }

        // Retrieve room information
        Room room = roomRepository.findByRoomType(selectedRoom);
        if (room == null) {
            result.rejectValue("roomType", "error.roomType", "Invalid room type selected");
            return "checkout";
        }

        // Calculate total price based on selected dates
        LocalDate checkinDate = LocalDate.parse(checkin);
        LocalDate checkoutDate = LocalDate.parse(checkout);
        long daysBetween = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        double totalPrice = room.getPrice() * daysBetween;

        // Set payment details
        payment.setPaymentDate(new Date());
        payment.setTotalPrice(totalPrice);
        payment.setCheckinDate(Date.from(checkinDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        payment.setCheckoutDate(Date.from(checkoutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        payment.setRoomType(selectedRoom); // Set room type in payment

        // Create booking details
        Booking booking = new Booking();
        booking.setStart(Date.from(checkinDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        booking.setEndDate(Date.from(checkoutDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        booking.setNotes("Booking notes");
        booking.setStatus("Confirmed");
        bookingRepository.save(booking);

        // Link payment with booking and save
        payment.setBooking(booking);
        paymentRepository.save(payment);

        return "redirect:/confirmation?bookingId=" + booking.getBookingId();
    }

    @GetMapping("/confirmation")
    public String confirmation(@RequestParam Integer bookingId, Model model) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            model.addAttribute("errorMessage", "Booking not found");
            return "error";
        }
        model.addAttribute("booking", booking);
        return "bookingconfirmation";
    }
}

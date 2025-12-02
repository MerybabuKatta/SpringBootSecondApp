package com.airline.booking.controller;

import com.airline.booking.entity.Booking;
import com.airline.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    
    private final BookingService bookingService;
    
    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest request) {
        return bookingService.createBooking(
                request.getFlightId(),
                request.getPassengerName(),
                request.getPassengerEmail(),
                request.getSeats()
        );
    }
    
    @PutMapping("/{bookingReference}/confirm")
    public Booking confirmBooking(@PathVariable String bookingReference) {
        return bookingService.confirmBooking(bookingReference);
    }
    
    @PutMapping("/{bookingReference}/cancel")
    public Booking cancelBooking(@PathVariable String bookingReference) {
        return bookingService.cancelBooking(bookingReference);
    }
    
    @GetMapping
    public List<Booking> getBookings(@RequestParam String email) {
        return bookingService.getBookingsByEmail(email);
    }
    
    public static class BookingRequest {
        private Long flightId;
        private String passengerName;
        private String passengerEmail;
        private Integer seats;
        
        // Getters and setters
        public Long getFlightId() { return flightId; }
        public void setFlightId(Long flightId) { this.flightId = flightId; }
        public String getPassengerName() { return passengerName; }
        public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
        public String getPassengerEmail() { return passengerEmail; }
        public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }
        public Integer getSeats() { return seats; }
        public void setSeats(Integer seats) { this.seats = seats; }
    }
}
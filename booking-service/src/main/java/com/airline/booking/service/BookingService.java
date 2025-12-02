package com.airline.booking.service;

import com.airline.booking.client.PricingServiceClient;
import com.airline.booking.entity.Booking;
import com.airline.booking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final PricingServiceClient pricingServiceClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    public Booking createBooking(Long flightId, String passengerName, String passengerEmail, Integer seats) {
        Double totalAmount = pricingServiceClient.calculatePrice(flightId, seats);
        
        Booking booking = new Booking();
        booking.setBookingReference(generateBookingReference());
        booking.setFlightId(flightId);
        booking.setPassengerName(passengerName);
        booking.setPassengerEmail(passengerEmail);
        booking.setNumberOfSeats(seats);
        booking.setTotalAmount(totalAmount);
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setBookingDate(LocalDateTime.now());
        booking.setLastModified(LocalDateTime.now());
        
        Booking savedBooking = bookingRepository.save(booking);
        
        // Send async message for inventory update
        kafkaTemplate.send("booking-created", savedBooking);
        
        return savedBooking;
    }
    
    public Booking confirmBooking(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        booking.setLastModified(LocalDateTime.now());
        
        return bookingRepository.save(booking);
    }
    
    public Booking cancelBooking(String bookingReference) {
        Booking booking = bookingRepository.findByBookingReference(bookingReference)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        booking.setLastModified(LocalDateTime.now());
        
        Booking cancelledBooking = bookingRepository.save(booking);
        
        // Send async message for inventory release
        kafkaTemplate.send("booking-cancelled", cancelledBooking);
        
        return cancelledBooking;
    }
    
    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByPassengerEmail(email);
    }
    
    private String generateBookingReference() {
        return "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
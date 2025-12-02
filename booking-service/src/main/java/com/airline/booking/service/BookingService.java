package com.airline.booking.service;

import com.airline.booking.client.PricingServiceClient;
import com.airline.booking.entity.Booking;
import com.airline.booking.repository.BookingRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final PricingServiceClient pricingServiceClient;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BookingService(BookingRepository bookingRepository, PricingServiceClient pricingServiceClient, KafkaTemplate<String, Object> kafkaTemplate) {
        this.bookingRepository = bookingRepository;
        this.pricingServiceClient = pricingServiceClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Booking createBooking(Long flightId, String passengerName, String passengerEmail, Integer seats) {
        Double totalAmount;
        try {
            totalAmount = pricingServiceClient.calculatePrice(flightId, seats);
        } catch (Exception e) {
            // Fallback pricing when pricing service is unavailable
            totalAmount = seats * 299.99; // Default price per seat
        }
        
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
        
        try {
            kafkaTemplate.send("booking-created", savedBooking);
        } catch (Exception e) {
            // Continue even if Kafka is unavailable
        }
        
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
        
        try {
            kafkaTemplate.send("booking-cancelled", cancelledBooking);
        } catch (Exception e) {
            // Continue even if Kafka is unavailable
        }
        
        return cancelledBooking;
    }
    
    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByPassengerEmail(email);
    }
    
    private String generateBookingReference() {
        return "BK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
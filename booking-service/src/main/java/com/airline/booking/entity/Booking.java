package com.airline.booking.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String bookingReference;
    private Long flightId;
    private String passengerName;
    private String passengerEmail;
    private Integer numberOfSeats;
    private Double totalAmount;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    
    private LocalDateTime bookingDate;
    private LocalDateTime lastModified;
    
    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED
    }
}
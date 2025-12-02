package com.airline.inventory.listener;

import com.airline.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingEventListener {
    
    private final InventoryService inventoryService;
    
    @KafkaListener(topics = "booking-created")
    public void handleBookingCreated(BookingEvent event) {
        log.info("Processing booking created event for flight: {}", event.getFlightId());
        boolean reserved = inventoryService.reserveSeats(event.getFlightId(), event.getSeats());
        if (!reserved) {
            log.error("Failed to reserve seats for booking: {}", event.getBookingReference());
        }
    }
    
    @KafkaListener(topics = "booking-cancelled")
    public void handleBookingCancelled(BookingEvent event) {
        log.info("Processing booking cancelled event for flight: {}", event.getFlightId());
        inventoryService.releaseSeats(event.getFlightId(), event.getSeats());
    }
    
    public static class BookingEvent {
        private String bookingReference;
        private Long flightId;
        private Integer seats;
        
        // Getters and setters
        public String getBookingReference() { return bookingReference; }
        public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
        public Long getFlightId() { return flightId; }
        public void setFlightId(Long flightId) { this.flightId = flightId; }
        public Integer getSeats() { return seats; }
        public void setSeats(Integer seats) { this.seats = seats; }
    }
}
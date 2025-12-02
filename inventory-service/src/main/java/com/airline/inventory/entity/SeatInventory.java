package com.airline.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "seat_inventory")
public class SeatInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long flightId;
    private Integer totalSeats;
    private Integer availableSeats;
    private Integer reservedSeats;
    
    @Version
    private Long version; // For optimistic locking
}
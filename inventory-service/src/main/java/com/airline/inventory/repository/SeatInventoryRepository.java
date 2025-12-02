package com.airline.inventory.repository;

import com.airline.inventory.entity.SeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface SeatInventoryRepository extends JpaRepository<SeatInventory, Long> {
    
    Optional<SeatInventory> findByFlightId(Long flightId);
    
    @Modifying
    @Query("UPDATE SeatInventory s SET s.availableSeats = s.availableSeats - :seats, " +
           "s.reservedSeats = s.reservedSeats + :seats WHERE s.flightId = :flightId AND s.availableSeats >= :seats")
    int reserveSeats(@Param("flightId") Long flightId, @Param("seats") Integer seats);
    
    @Modifying
    @Query("UPDATE SeatInventory s SET s.availableSeats = s.availableSeats + :seats, " +
           "s.reservedSeats = s.reservedSeats - :seats WHERE s.flightId = :flightId")
    int releaseSeats(@Param("flightId") Long flightId, @Param("seats") Integer seats);
}
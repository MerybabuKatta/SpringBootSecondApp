package com.airline.search.repository;

import com.airline.search.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    
    @Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination " +
           "AND f.departureTime >= :departureDate AND f.availableSeats > 0")
    List<Flight> findAvailableFlights(@Param("origin") String origin, 
                                    @Param("destination") String destination,
                                    @Param("departureDate") LocalDateTime departureDate);
}
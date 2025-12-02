package com.airline.search.service;

import com.airline.search.entity.Flight;
import com.airline.search.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightSearchService {
    
    private final FlightRepository flightRepository;
    
    public List<Flight> searchFlights(String origin, String destination, LocalDateTime departureDate) {
        return flightRepository.findAvailableFlights(origin, destination, departureDate);
    }
    
    public Flight getFlightById(Long flightId) {
        return flightRepository.findById(flightId).orElse(null);
    }
}
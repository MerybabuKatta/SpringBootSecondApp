package com.airline.search.controller;

import com.airline.search.entity.Flight;
import com.airline.search.service.FlightSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightSearchController {
    
    private final FlightSearchService flightSearchService;
    
    @GetMapping("/search")
    public List<Flight> searchFlights(
            @RequestParam String origin,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDate) {
        return flightSearchService.searchFlights(origin, destination, departureDate);
    }
    
    @GetMapping("/{flightId}")
    public Flight getFlightById(@PathVariable Long flightId) {
        return flightSearchService.getFlightById(flightId);
    }
}
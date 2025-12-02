package com.airline.pricing.service;

import com.airline.pricing.client.SearchServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricingService {
    
    private final SearchServiceClient searchServiceClient;
    
    public Double calculatePrice(Long flightId, Integer numberOfSeats) {
        SearchServiceClient.FlightDto flight = searchServiceClient.getFlightById(flightId);
        
        if (flight == null) {
            throw new RuntimeException("Flight not found");
        }
        
        Double basePrice = flight.getBasePrice();
        Double dynamicMultiplier = calculateDynamicMultiplier(flight.getAvailableSeats());
        
        return basePrice * numberOfSeats * dynamicMultiplier;
    }
    
    private Double calculateDynamicMultiplier(Integer availableSeats) {
        // Dynamic pricing based on availability
        if (availableSeats < 10) {
            return 1.5; // High demand
        } else if (availableSeats < 50) {
            return 1.2; // Medium demand
        } else {
            return 1.0; // Normal pricing
        }
    }
}
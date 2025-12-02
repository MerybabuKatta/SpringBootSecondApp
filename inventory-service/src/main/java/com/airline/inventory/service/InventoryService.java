package com.airline.inventory.service;

import com.airline.inventory.entity.SeatInventory;
import com.airline.inventory.repository.SeatInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    
    private final SeatInventoryRepository seatInventoryRepository;
    
    @Transactional
    public boolean reserveSeats(Long flightId, Integer seats) {
        int updated = seatInventoryRepository.reserveSeats(flightId, seats);
        return updated > 0;
    }
    
    @Transactional
    public void releaseSeats(Long flightId, Integer seats) {
        seatInventoryRepository.releaseSeats(flightId, seats);
    }
    
    public SeatInventory getInventoryByFlightId(Long flightId) {
        return seatInventoryRepository.findByFlightId(flightId).orElse(null);
    }
}
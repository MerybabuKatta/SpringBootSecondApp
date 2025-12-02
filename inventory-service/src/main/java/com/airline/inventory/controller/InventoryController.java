package com.airline.inventory.controller;

import com.airline.inventory.entity.SeatInventory;
import com.airline.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService inventoryService;
    
    @GetMapping("/flight/{flightId}")
    public SeatInventory getInventory(@PathVariable Long flightId) {
        return inventoryService.getInventoryByFlightId(flightId);
    }
    
    @PostMapping("/reserve")
    public boolean reserveSeats(@RequestParam Long flightId, @RequestParam Integer seats) {
        return inventoryService.reserveSeats(flightId, seats);
    }
    
    @PostMapping("/release")
    public void releaseSeats(@RequestParam Long flightId, @RequestParam Integer seats) {
        inventoryService.releaseSeats(flightId, seats);
    }
}
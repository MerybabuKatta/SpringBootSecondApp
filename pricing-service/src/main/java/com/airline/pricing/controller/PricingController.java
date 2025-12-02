package com.airline.pricing.controller;

import com.airline.pricing.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pricing")
@RequiredArgsConstructor
public class PricingController {
    
    private final PricingService pricingService;
    
    @GetMapping("/calculate")
    public Double calculatePrice(@RequestParam Long flightId, @RequestParam Integer seats) {
        return pricingService.calculatePrice(flightId, seats);
    }
}
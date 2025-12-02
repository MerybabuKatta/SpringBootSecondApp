package com.airline.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "pricing-service")
public interface PricingServiceClient {
    
    @GetMapping("/api/pricing/calculate")
    Double calculatePrice(@RequestParam Long flightId, @RequestParam Integer seats);
}
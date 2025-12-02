package com.airline.payment.controller;

import com.airline.payment.entity.Payment;
import com.airline.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    
    private final PaymentService paymentService;
    
    @PostMapping("/process")
    public Payment processPayment(@RequestBody PaymentRequest request) {
        return paymentService.processPayment(
                request.getBookingReference(),
                request.getAmount(),
                request.getPaymentMethod()
        );
    }
    
    @PostMapping("/{paymentId}/refund")
    public Payment refundPayment(@PathVariable String paymentId) {
        return paymentService.refundPayment(paymentId);
    }
    
    @GetMapping("/booking/{bookingReference}")
    public List<Payment> getPaymentsByBooking(@PathVariable String bookingReference) {
        return paymentService.getPaymentsByBooking(bookingReference);
    }
    
    public static class PaymentRequest {
        private String bookingReference;
        private Double amount;
        private String paymentMethod;
        
        // Getters and setters
        public String getBookingReference() { return bookingReference; }
        public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    }
}
package com.airline.payment.service;

import com.airline.payment.entity.Payment;
import com.airline.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    
    public Payment processPayment(String bookingReference, Double amount, String paymentMethod) {
        Payment payment = new Payment();
        payment.setPaymentId(generatePaymentId());
        payment.setBookingReference(bookingReference);
        payment.setAmount(amount);
        payment.setCurrency("USD");
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());
        
        // Mock payment processing
        boolean paymentSuccess = mockPaymentGateway(amount, paymentMethod);
        
        if (paymentSuccess) {
            payment.setStatus(Payment.PaymentStatus.SUCCESS);
            payment.setTransactionId(UUID.randomUUID().toString());
        } else {
            payment.setStatus(Payment.PaymentStatus.FAILED);
        }
        
        return paymentRepository.save(payment);
    }
    
    public Payment refundPayment(String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        if (payment.getStatus() == Payment.PaymentStatus.SUCCESS) {
            payment.setStatus(Payment.PaymentStatus.REFUNDED);
            return paymentRepository.save(payment);
        }
        
        throw new RuntimeException("Payment cannot be refunded");
    }
    
    public List<Payment> getPaymentsByBooking(String bookingReference) {
        return paymentRepository.findByBookingReference(bookingReference);
    }
    
    private boolean mockPaymentGateway(Double amount, String paymentMethod) {
        // Mock payment processing - 90% success rate
        return Math.random() > 0.1;
    }
    
    private String generatePaymentId() {
        return "PAY" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
package com.example.paymentservice.controllers;

import com.example.paymentservice.dtos.InitiatePaymentRequestDto;
import com.example.paymentservice.services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public String initiatePayment(@RequestBody InitiatePaymentRequestDto requestDto)  {
        try {
            return paymentService.initiatePayment(
                    requestDto.getOrderId(),
                    requestDto.getAmount()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/sample")
    public String sampleAPI() {
        return "Hello from Scaler";
    }
}

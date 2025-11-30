package com.ecom.paymentservice.controller;

import com.ecom.paymentservice.dto.PaymentRequestDTO;
import com.ecom.paymentservice.dto.PaymentResponseDTO;
import com.ecom.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        PaymentResponseDTO paymentResponseDTO = paymentService.processPayment(paymentRequestDTO);
        return  ResponseEntity.ok(paymentResponseDTO);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getPaymentByOrderId(@PathVariable String orderId) {
        List<PaymentResponseDTO> responseDTOS = paymentService.getPaymentByOrderId(orderId);
        return  ResponseEntity.ok(responseDTOS);
    }
}

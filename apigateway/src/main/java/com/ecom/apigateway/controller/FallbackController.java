package com.ecom.apigateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/product")
    public ResponseEntity<String> fallbackProduct(){
        return ResponseEntity.ok("Product service currently unavailable. Please try again after some time");
    }

    @GetMapping("/fallback/payment")
    public ResponseEntity<String> fallbackPayment(){
        return ResponseEntity.ok("Payment service currently unavailable. Please try again after some time");
    }
}

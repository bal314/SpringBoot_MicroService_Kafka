package com.ecom.orderservice.controller;

import com.ecom.orderservice.dto.OrderRequestDTO;
import com.ecom.orderservice.dto.OrderResponseDTO;
import com.ecom.orderservice.dto.OrderStatus;
import com.ecom.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    public OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        OrderResponseDTO orderResponseDTO = orderService.placeOrder(orderRequestDTO);

        return ResponseEntity.ok(orderResponseDTO);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderByOrderId(@PathVariable String orderId){
        OrderResponseDTO orderResponseDTO = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.ok(orderResponseDTO);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getOrderByCustomer(@PathVariable String customerId){
        List<OrderResponseDTO> orderResponseDTOList = orderService.getOrderByCustomer(customerId);
        return ResponseEntity.ok(orderResponseDTOList);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus orderStatus){
         orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok("order status updated successfully");
    }
}

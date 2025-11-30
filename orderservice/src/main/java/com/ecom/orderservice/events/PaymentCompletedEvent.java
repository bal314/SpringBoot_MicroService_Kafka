package com.ecom.orderservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class PaymentCompletedEvent {
    private String orderId;
    private String paymentStatus;
}
package com.ecom.paymentservice.kafkaevents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderCreatedEvent {
    private String orderId;
    private String customerId;
    private String amount;
}

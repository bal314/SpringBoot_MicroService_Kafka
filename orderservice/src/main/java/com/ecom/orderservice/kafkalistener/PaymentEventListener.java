package com.ecom.orderservice.kafkalistener;

import com.ecom.orderservice.dto.OrderStatus;
import com.ecom.orderservice.events.PaymentCompletedEvent;
import com.ecom.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentEventListener {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "payment-events", groupId = "order-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // The readValue method takes the JSON string and the Target Class type
            PaymentCompletedEvent paymentCompletedEvent = objectMapper.readValue(message, PaymentCompletedEvent.class);

            log.info(" processing payment Completed Event---------------------");

            orderService.updateOrderStatus(paymentCompletedEvent.getOrderId(), OrderStatus.valueOf(paymentCompletedEvent.getPaymentStatus()));
            log.info(" order status updated Completed Event---------------------");

        } catch (Exception exception){
            log.error("Error in processing orderCreatedEvent: {}",exception.getMessage());
            throw new RuntimeException("Error in processing orderCreatedEvent", exception);
        }
    }

}

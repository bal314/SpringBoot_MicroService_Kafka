package com.ecom.paymentservice.kafkalistener;

import com.ecom.paymentservice.dto.PaymentRequestDTO;
import com.ecom.paymentservice.kafkaevents.OrderCreatedEvent;
import com.ecom.paymentservice.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventListener {

    @Autowired
    private final PaymentService paymentService;

    @KafkaListener(topics = "order-event",
            groupId = "payment-service-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(String orderCreatedEventString) {

        try {
            log.info("  ------------------------"+orderCreatedEventString);
            ObjectMapper objectMapper = new ObjectMapper();
            // The readValue method takes the JSON string and the Target Class type
            OrderCreatedEvent orderCreatedEvent = objectMapper.readValue(orderCreatedEventString, OrderCreatedEvent.class);

            PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
            paymentRequestDTO.setOrderId(orderCreatedEvent.getOrderId());
            paymentRequestDTO.setAmount(Double.parseDouble(orderCreatedEvent.getAmount()));
            paymentRequestDTO.setCustomerId(orderCreatedEvent.getCustomerId());
            log.info(" processing orderCreatedEvent---------------------");

            paymentService.processPayment(paymentRequestDTO);
        } catch (Exception exception) {
            log.error("Error in processing orderCreatedEvent: {}",exception.getMessage());
            throw new RuntimeException("Error in processing orderCreatedEvent", exception);
        }
    }
}

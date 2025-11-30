package com.ecom.paymentservice.service;

import com.ecom.paymentservice.dto.PaymentRequestDTO;
import com.ecom.paymentservice.dto.PaymentResponseDTO;
import com.ecom.paymentservice.dto.PaymentStatus;
import com.ecom.paymentservice.entity.Payment;
import com.ecom.paymentservice.kafkaevents.PaymentCompletedEvent;
import com.ecom.paymentservice.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderClient orderClient;

    @Autowired
    KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;

    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequestDTO){
        String paymentId = generatePaymentId();
        Payment payment = new Payment();
        payment.setPaymentId(paymentId);
        payment.setOrderId(paymentRequestDTO.getOrderId());
        payment.setCustomerId(paymentRequestDTO.getCustomerId());
        payment.setAmount(paymentRequestDTO.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        boolean success = new Random().nextBoolean();
        String status = null;
        if (success){
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setTransactionId(UUID.randomUUID().toString());
            status="CONFIRMED";
           // orderClient.updateOrderStatus(paymentRequestDTO.getOrderId(), "CONFIRMED");
            //
        }else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            payment.setTransactionId("N/A");
            status="CANCELLED";
            // orderClient.updateOrderStatus(paymentRequestDTO.getOrderId(), "CANCELLED");
        }
        payment = paymentRepository.save(payment);
        //send the kafka event for updating the payment status
        updatePaymentStatus(payment, status);
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        paymentResponseDTO.setPaymentId(paymentId);
        paymentResponseDTO.setOrderId(payment.getOrderId());
        paymentResponseDTO.setCustomerId(payment.getCustomerId());
        paymentResponseDTO.setAmount(payment.getAmount());
        paymentResponseDTO.setPaymentStatus(payment.getPaymentStatus());
        paymentResponseDTO.setTransactionId(payment.getTransactionId());
        return paymentResponseDTO;
    }



    private String generatePaymentId() {
        return "pay-"+ UUID.randomUUID().toString().substring(0,8);
    }

    public List<PaymentResponseDTO> getPaymentByOrderId(String orderId) {

        List<Payment> payments = paymentRepository.findByOrderId(orderId);
        if (payments == null){
            return null;
        }

        List<PaymentResponseDTO> list = new ArrayList<PaymentResponseDTO>();
        payments.forEach(payment -> {
            PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
            paymentResponseDTO.setPaymentId(payment.getPaymentId());
            paymentResponseDTO.setOrderId(payment.getOrderId());
            paymentResponseDTO.setCustomerId(payment.getCustomerId());
            paymentResponseDTO.setAmount(payment.getAmount());
            paymentResponseDTO.setPaymentStatus(payment.getPaymentStatus());
            paymentResponseDTO.setTransactionId(payment.getTransactionId());
            list.add(paymentResponseDTO);
        });

        return list;
    }

    // send the kafka message for payment completed process
    private void updatePaymentStatus(Payment payment, String status) {
        try {
            PaymentCompletedEvent paymentCompletedEvent = new PaymentCompletedEvent();
            paymentCompletedEvent.setOrderId(payment.getOrderId());
            paymentCompletedEvent.setPaymentStatus(status);
            kafkaTemplate.send("payment-events", paymentCompletedEvent);
            log.info("Successful sent paymentCompletedEvent to kafka listener");
        } catch (Exception e) {
            log.error("Error in sending paymentCompletedEvent: {}", e.getMessage());
            throw new RuntimeException("Error in sending paymentCompletedEvent", e);
        }
    }
}

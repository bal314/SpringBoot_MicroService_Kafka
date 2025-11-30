package com.ecom.paymentservice.service;

import com.ecom.paymentservice.dto.OrderStatusUpdateRequestDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderClient {

    private final RestTemplate restTemplate;

    public OrderClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public void updateOrderStatus(String orderId, String status) {
        String url = "http://localhost:6001/api/orders/"+orderId+"/status?orderStatus="+status;
        OrderStatusUpdateRequestDTO orderStatusUpdateRequestDTO = new OrderStatusUpdateRequestDTO(orderId, status);
        String response = restTemplate.patchForObject(url,orderStatusUpdateRequestDTO, String.class);
        System.out.println("Order status updates successfully "+response);
    }
}

package com.ecom.orderservice.dto;

import lombok.Data;

@Data
public class OrderItemResponseDTO {

    private String orderItemId;
    private String orderId;
    private Long productId;
    private Integer quantity;
    private Double price;

}

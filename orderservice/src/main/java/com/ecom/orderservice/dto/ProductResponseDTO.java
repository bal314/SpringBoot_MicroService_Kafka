package com.ecom.orderservice.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long productId;
    private String name;
    private Integer stockQuantity;
    private Double price;

}

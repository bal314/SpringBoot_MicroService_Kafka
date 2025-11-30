package com.ecom.productservice.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Boolean inStock;
    private String categoryName;
}
